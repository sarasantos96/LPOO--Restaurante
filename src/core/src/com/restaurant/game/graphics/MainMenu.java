package com.restaurant.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.restaurant.game.Restaurant;

public class MainMenu implements Screen {
    //Screen Dimensions
    public final static int MENU_WIDTH = 3200;
    public final static int MENU_HEIGHT = 2400;
    //Camera and Viewport
    private OrthographicCamera cam;
    private Viewport viewport;
    //MainMenu variables
    private Restaurant restaurant;
    private Texture background;
    private Sprite playBtn;
    private Sprite scoresBtn;
    private Sprite exitBtn;

    /**
     * Constructor of class MainMenu
     * Creates a new Main Menu screen
     * @param restaurant
     */
    public MainMenu( Restaurant restaurant){
        this.restaurant = restaurant;

        cam = new OrthographicCamera();
        //cam.setToOrtho(false, MENU_WIDTH,MENU_HEIGHT);
        viewport = new StretchViewport(MENU_WIDTH , MENU_HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth() /2, viewport.getWorldHeight()/2, 0);
        viewport.apply();

        //Initializes all textures
        background = new Texture("background.jpg");

        Texture playTexture = new Texture("playbox.png");
        playBtn = new Sprite();
        playBtn.setBounds(200,100,800,329);
        playBtn.setRegion(playTexture);

        Texture scoresTexture = new Texture("scoresbox.png");
        scoresBtn = new Sprite();
        scoresBtn.setBounds(1200,100,800,329);
        scoresBtn.setRegion(scoresTexture);

        Texture exitTexture = new Texture("exitbox.png");
        exitBtn = new Sprite();
        exitBtn.setBounds(2200,100,800,329);
        exitBtn.setRegion(exitTexture);

        //Play background music
        restaurant.mainTheme.play();
        restaurant.mainTheme.setLooping(true);

    }

    /**
     * Checks if the Play button has been touch
     * @param x x coordenate
     * @param y y coordenate
     * @return true if touch, otherwise false
     */
    public boolean touchPlayBtn(float x, float y){
        boolean t ;

        Rectangle rect = playBtn.getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Checks if the Scores button has been touch
     * @param x x coordinate
     * @param y y coordinate
     * @return true if touch, otherwise false
     */
    public boolean touchScoresBtn(float x, float y){
        boolean t ;

        Rectangle rect = scoresBtn.getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Checks if the Exit button has been touch
     * @param x x coordenate
     * @param y y coordenate
     * @return true if touch, otherwise false
     */
    public boolean touchExitBtn(float x, float y){
        boolean t ;

        Rectangle rect = exitBtn.getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Handles screen input
     * Checks and responds if the user touched a critical screen area
     */
    public void handleInput() {
        Vector3 touchPos = new Vector3();

        if(Gdx.input.justTouched()){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(touchPos);

            //Check if any button is pressed
            if( touchPlayBtn(touchPos.x, touchPos.y) ) {
                restaurant.mainTheme.stop();
                restaurant.playScreen = new PlayScreen(restaurant);
                restaurant.setScreen(restaurant.playScreen);
            }else if(touchScoresBtn(touchPos.x,touchPos.y)){
                restaurant.setScreen(restaurant.scoresScreen);
            }else if(touchExitBtn(touchPos.x,touchPos.y)){
                Gdx.app.exit();
            }
        }
    }

    /**
     * Updates Main Menu screen
     * @param dt current time
     */
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        restaurant.batch.setProjectionMatrix(cam.combined);
        restaurant.batch.begin();
        restaurant.batch.draw(background,0,0);
        playBtn.draw(restaurant.batch);
        scoresBtn.draw(restaurant.batch);
        exitBtn.draw(restaurant.batch);
        restaurant.batch.end();
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
