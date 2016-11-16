package com.restaurant.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.restaurant.game.Restaurant;
import com.restaurant.game.Tools.*;
import com.restaurant.game.logic.Client;

public class PlayScreen implements Screen {
    private Restaurant restaurant;
    //Camera and Viewport
    private OrthographicCamera cam;
    private Viewport viewport;
    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Box2DCreator box2DCreator;
    //Vector to store touch screen coordenates
    private Vector3 touchPos;
    //Game HUD
    private com.restaurant.game.Tools.HUD hud;
    //Background and store button
    private Sprite storeButton;
    private TextureRegion background;

    /**
     * Construtor of class PlayScreen
     * Creates a new game screen
     * @param restaurant
     */
    public PlayScreen(Restaurant restaurant){
        this.restaurant = restaurant;

        //Initializes camera and viewport
        cam = new OrthographicCamera();
        //cam.setToOrtho(false, Restaurant.WIDTH,Restaurant.HEIGHT);
        viewport = new StretchViewport(Restaurant.WIDTH , Restaurant.HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth() /2, viewport.getWorldHeight()/2, 0);
        viewport.apply();

        //Initializes game HUD
        this.hud = new com.restaurant.game.Tools.HUD(restaurant.logic,restaurant.batch,0,0,1,2,3);

        //Initializes touch vector
        touchPos = new Vector3(0,0,0);

        //Initializes background
        background = new TextureRegion(new Texture("back.png"), 1210, 800);
        background.setRegionWidth(Restaurant.WIDTH);
        background.setRegionWidth(Restaurant.HEIGHT);

        //Initializes Box2D Variables
        world = restaurant.world;
        b2dr = new Box2DDebugRenderer();
        box2DCreator = new Box2DCreator(world);
        box2DCreator.createGround();
        box2DCreator.createCounter();

        //Store Button
        TextureRegion button = new TextureRegion(new Texture("store_button.png"),0,0,455,305);
        storeButton = new Sprite();
        storeButton.setBounds(Restaurant.WIDTH- 225, Restaurant.HEIGHT - 100 , 200,100);
        storeButton.setRegion(button);

        //Background Music
        restaurant.playMusic.play();
        restaurant.playMusic.setLooping(true);
    }

    /**
     * Returns Box2D world
     * @return world
     */
    public World getWorld(){
        return world;
    }

    /**
     * Returns game HUD
     * @return hud
     */
    public com.restaurant.game.Tools.HUD getHud(){ return hud;}

    /**
     * Returns restaurant (main class)
     * @return restaurant
     */
    public Restaurant getRestaurant(){ return restaurant;}

    public boolean touchStoreBtn(float x, float y){
        boolean t;

        Rectangle rect = storeButton.getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Handles screen input
     * Checks and responds if the user touched a critical screen area
     */
    public void handleInput(float dt) {
        if(Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            if(touchPos.y <= 185 && touchPos.x <= Restaurant.WIDTH - 400) {
                restaurant.logic.getPlayer().updateFlags(touchPos.x);
            }else if(restaurant.logic.getBurgerMachine().beginCook(touchPos.x,touchPos.y)){
                restaurant.logic.getBurgerMachine().setProducing();
            }else if(restaurant.logic.getFriesMachine().beginCook(touchPos.x,touchPos.y)){
                restaurant.logic.getFriesMachine().setProducing();
            }else if(restaurant.logic.getDrinkMachine().beginCook(touchPos.x,touchPos.y)){
                restaurant.logic.getDrinkMachine().setProducing();
            }else if(restaurant.logic.getBurgerMachine().isTouch(touchPos.x, touchPos.y)){
                restaurant.logic.getBurgerMachine().serveClient(restaurant.logic.getFirstClient());
            }else if(restaurant.logic.getFriesMachine().isTouch(touchPos.x, touchPos.y)){
                restaurant.logic.getFriesMachine().serveClient(restaurant.logic.getFirstClient());
            }else if(restaurant.logic.getDrinkMachine().isTouch(touchPos.x, touchPos.y)){
                restaurant.logic.getDrinkMachine().serveClient(restaurant.logic.getFirstClient());
            }else if(touchStoreBtn(touchPos.x,touchPos.y)){
                restaurant.playScreen.pause();
                restaurant.storeScreen.getHud().setStoreHUDMoney(restaurant.logic.getMoney());
                restaurant.setScreen(restaurant.storeScreen);
            }
        }
    }

    /**
     * Updates the Play Screen
     * @param delta
     */
    public void update(float delta){
        handleInput(delta);
        world.step(1/60f, 6, 2);

        //Checks if player lost
        if(restaurant.logic.lostGame()){
            restaurant.playMusic.stop();
            dispose();
            restaurant.gameOverScreen = new GameOver(restaurant,restaurant.logic);
            restaurant.setScreen(restaurant.gameOverScreen);
        }
        restaurant.logic.update(delta, touchPos.x, touchPos.y);
        hud.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        restaurant.batch.setProjectionMatrix(cam.combined);
        restaurant.batch.begin();

        //Draws the background
         restaurant.batch.draw(background,0,0,Restaurant.WIDTH,Restaurant.HEIGHT);

        //Draws the player
        restaurant.logic.getPlayer().draw(restaurant.batch);

        //Draws all the machines
        restaurant.logic.getBurgerMachine().draw(restaurant.batch);
        restaurant.logic.getFriesMachine().draw(restaurant.batch);
        restaurant.logic.getDrinkMachine().draw(restaurant.batch);
        //And the respective cook buttons
        restaurant.logic.getBurgerMachine().getCookButton().draw(restaurant.batch);
        restaurant.logic.getFriesMachine().getCookButton().draw(restaurant.batch);
        restaurant.logic.getDrinkMachine().getCookButton().draw(restaurant.batch);

        //Draw store button
        storeButton.draw(restaurant.batch);

        //Draws the order of the first client in line if he's waiting
        if(restaurant.logic.gotClients()) {
            if (restaurant.logic.getFirstClient().getState() == Client.ClientState.WAITING)
                restaurant.logic.getFirstClient().getSpriteOrder().draw(restaurant.batch);
        }

        //Draws alll the clients
        for(int i = 0; i < restaurant.logic.getClients().size(); i++){
            restaurant.logic.getClients().get(i).draw(restaurant.batch);
        }

        restaurant.batch.end();

        //Draws the hud
        restaurant.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
