package com.restaurant.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.restaurant.game.Restaurant;
import com.restaurant.game.Tools.StoreHUD;

public class Store implements Screen {
    //Screen Dimensions
    private static final int STORE_WIDTH =700;
    private static final int STORE_HEIGHT = 525;
    //Camera and Viewport
    private OrthographicCamera cam;
    private Viewport viewport;
    //Store Variables
    private Restaurant restaurant;
    private Texture background;
    private Sprite title;
    private Sprite burgerUpgradeBtn;
    private Sprite friesUpgradeBtn;
    private Sprite drinkUpgradeBtn;
    private Sprite backBtn;
    private StoreHUD hud;

    /**
     * Construtor of class Store
     * Creates a new Store for machine updates
     * @param restaurant
     */
    public Store(Restaurant restaurant){
        this.restaurant = restaurant;

        //Initializes camera and viewport
        cam = new OrthographicCamera();
        //cam.setToOrtho(false, STORE_WIDTH,STORE_HEIGHT);
        viewport = new StretchViewport(STORE_WIDTH, STORE_HEIGHT , cam);
        cam.position.set(viewport.getWorldWidth() /2, viewport.getWorldHeight()/2, 0);
        viewport.apply();

        //Store background
        background = new Texture("background.png");

        //Back arrow (return to game)
        TextureRegion back = new TextureRegion(new Texture("back_arrow.png"),0,0,600,456);
        backBtn = new Sprite();
        backBtn.setBounds(20,460,70,50);
        backBtn.setRegion(back);

        //Store title
        TextureRegion store = new TextureRegion(new Texture("store_title.png"),0,0,600,200);
        title = new Sprite();
        title.setBounds(200,420,320,100);
        title.setRegion(store);

        //First burger machine update button
        TextureRegion burger = new TextureRegion(new Texture("burger_upgrade1.png"),0,0,796,892);
        burgerUpgradeBtn = new Sprite();
        burgerUpgradeBtn.setBounds(5,70,250,350);
        burgerUpgradeBtn.setRegion(burger);

        //First fries machine update button
        TextureRegion fries = new TextureRegion(new Texture("fries_upgrade1.png"),0,0,796,892);
        friesUpgradeBtn = new Sprite();
        friesUpgradeBtn.setBounds(230,70,250,350);
        friesUpgradeBtn.setRegion(fries);

        //First drink machine update button
        TextureRegion drink = new TextureRegion(new Texture("drink_upgrade1.png"),0,0,796,892);
        drinkUpgradeBtn = new Sprite();
        drinkUpgradeBtn.setBounds(450,70,250,350);
        drinkUpgradeBtn.setRegion(drink);

        //Store HUD
        //Displays money
        this.hud = new StoreHUD(restaurant.batch, restaurant.logic.getMoney());
    }

    /**
     * Return store HUD
     * @return hud
     */
    public StoreHUD getHud() {
        return hud;
    }

    /**
     * Checks if burger update button has been touched
     * @param x x coordenate
     * @param y y coordenate
     * @return true if touch, otherwise false
     */
    public boolean touchBurgerUpgrade(float x, float y){
        boolean t;

        Rectangle rect = burgerUpgradeBtn.getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Checks if fries update button has been touched
     * @param x x coordenate
     * @param y y coordenate
     * @return true if touch, otherwise false
     */
    public boolean touchFriesUpgrade(float x, float y){
        boolean t;

        Rectangle rect = friesUpgradeBtn.getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Checks if drink update button has been touched
     * @param x x coordenate
     * @param y y coordenate
     * @return true if touch, otherwise false
     */
    public boolean touchDrinkUpgrade(float x, float y){
        boolean t;

        Rectangle rect = drinkUpgradeBtn.getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Checks if back arrow has been touched
     * @param x x coordenate
     * @param y y coordenate
     * @return true if touch, otherwise false
     */
    public boolean touchBackArrow(float x, float y){
        boolean t;

        Rectangle rect = backBtn.getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Updates the upgrade buttons of each machine
     * The correct upgrade button is display according to the upgrade level
     */
    public void updateButtons(){
        switch(restaurant.logic.getBurgerMachine().getUpgrade()){
            case UPGRADE2:
                TextureRegion up2 = new TextureRegion(new Texture("burger_upgrade2.png"),0,0,796,892);
                burgerUpgradeBtn.setRegion(up2);
                break;
            case UPGRADE3:
                TextureRegion up3 = new TextureRegion(new Texture("burger_upgrade3.png"),0,0,796,892);
                burgerUpgradeBtn.setRegion(up3);
                break;
            default:
                break;
        }

        switch(restaurant.logic.getFriesMachine().getUpgrade()){
            case UPGRADE2:
                TextureRegion up2 = new TextureRegion(new Texture("fries_upgrade2.png"),0,0,796,892);
                friesUpgradeBtn.setRegion(up2);
                break;
            case UPGRADE3:
                TextureRegion up3 = new TextureRegion(new Texture("fries_upgrade3.png"),0,0,796,892);
                friesUpgradeBtn.setRegion(up3);
                break;
            default:
                break;
        }

        switch(restaurant.logic.getDrinkMachine().getUpgrade()){
            case UPGRADE2:
                TextureRegion up2 = new TextureRegion(new Texture("drink_upgrade2.png"),0,0,796,892);
                drinkUpgradeBtn.setRegion(up2);
                break;
            case UPGRADE3:
                TextureRegion up3 = new TextureRegion(new Texture("drink_upgrade3.png"),0,0,796,892);
                drinkUpgradeBtn.setRegion(up3);
                break;
            default:
                break;
        }
    }

    /**
     * Handles screen input
     * Checks and responds if the user touched a critical screen area
     */
    public void handleInput(float dt){
        Vector3 touchPos = new Vector3();

        if(Gdx.input.justTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            if(touchBackArrow(touchPos.x,touchPos.y)) {
                restaurant.setScreen(restaurant.playScreen);
            }else if(touchBurgerUpgrade(touchPos.x,touchPos.y)){
                restaurant.logic.upgradeBurgerMachine();
                updateButtons();
            }else if(touchFriesUpgrade(touchPos.x,touchPos.y)){
                restaurant.logic.upgradeFriesMachine();
                updateButtons();
            }else if(touchDrinkUpgrade(touchPos.x,touchPos.y)){
                restaurant.logic.upgradeDrinkMachine();
                updateButtons();
            }
        }
    }

    /**
     * Updates Store screen
     * @param dt current time
     */
    public void update(float dt){
        handleInput(dt);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        restaurant.batch.setProjectionMatrix(cam.combined);
        restaurant.batch.begin();

        //Draws background
        restaurant.batch.draw(background,0,0);
        //Draws title
        title.draw(restaurant.batch);
        //Draws back arrow
        backBtn.draw(restaurant.batch);
        //Draws upgrade buttons
        burgerUpgradeBtn.draw(restaurant.batch);
        friesUpgradeBtn.draw(restaurant.batch);
        drinkUpgradeBtn.draw(restaurant.batch);

        restaurant.batch.end();
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
