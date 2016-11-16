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
import com.restaurant.game.Tools.ScoresHUD;

public class Scores implements Screen{
    //Screen Dimensions
    public final static int SCORES_WIDTH = 700;
    public final static int SCORES_HEIGHT = 525;
    //Camera and Viewport
    private OrthographicCamera cam;
    private Viewport viewport;
    //High Scores
    private int first;
    private int second;
    private int third;
    //Scores Variables
    private Restaurant restaurant;
    private Texture background;
    private Texture one;
    private Texture two;
    private Texture three;
    private Sprite backBtn;
    private Sprite title;
    private ScoresHUD hud;

    /**
     * Constructor of class Scores
     * Creates a new Scores screen to show the three high scores
     * @param restaurant
     */
    public Scores(Restaurant restaurant){
        this.restaurant = restaurant;

        //Initializes camera and viewport
        cam = new OrthographicCamera();
        //cam.setToOrtho(false, SCORES_WIDTH,SCORES_HEIGHT);
        viewport = new StretchViewport(SCORES_WIDTH, SCORES_HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth() /2, viewport.getWorldHeight()/2, 0);
        viewport.apply();

        Gdx.app.log("first", restaurant.scoresManager.getFirst().toString());
        /*System.out.println(restaurant.scoresManager.getFirst());
        System.out.println(restaurant.scoresManager.getSecond());
        System.out.println(restaurant.scoresManager.getThird());*/

        //Initializes high scores
        this.first = restaurant.scoresManager.getFirst();
        this.second = restaurant.scoresManager.getSecond();
        this.third = restaurant.scoresManager.getThird();

        //Initializes textures
        background = new Texture("background.png");

        one = new Texture("1.png");
        two = new Texture("2.png");
        three = new Texture("3.png");

        TextureRegion back = new TextureRegion(new Texture("back_arrow.png"),0,0,600,456);
        backBtn = new Sprite();
        backBtn.setBounds(20,450,70,50);
        backBtn.setRegion(back);

        TextureRegion store = new TextureRegion(new Texture("scores_title.png"),0,0,600,200);
        title = new Sprite();
        title.setBounds(200,420,320,100);
        title.setRegion(store);

        //Scores HUD
        hud = new ScoresHUD(restaurant.batch, first, second, third);
    }

    /**
     * Updates the high scores showing on screen
     */
    public void updateScores(){
        this.first = restaurant.scoresManager.getFirst();
        this.second = restaurant.scoresManager.getSecond();
        this.third = restaurant.scoresManager.getThird();
        Gdx.app.log("first", restaurant.scoresManager.getFirst().toString());
        System.out.println(this.first);
        System.out.println(this.second);
        System.out.println(this.third);
        this.hud.setFirst(this.first);
        this.hud.setSecond(this.second);
        this.hud.setThird(this.third);
    }

    /**
     * Checks of back arrow has been touch
     * @param x x coordinate
     * @param y y coordinate
     * @return true if touch, false otherwise
     */
    public boolean touchBackArrow(float x, float y){
        boolean t;

        Rectangle rect = backBtn.getBoundingRectangle();
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
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            if(touchBackArrow(touchPos.x, touchPos.y)) {
                restaurant.setScreen(restaurant.mainMenu);
            }
        }
    }

    /**
     * Updates Scores screen
     * @param dt current time
     */
    public void update(float dt) {
        updateScores();
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

        //Draws background
        restaurant.batch.draw(background,0,0);
        //Draws scores
        restaurant.batch.draw(one,90,300);
        restaurant.batch.draw(two,90,200);
        restaurant.batch.draw(three,90,100);
        //Draws title
        title.draw(restaurant.batch);
        //Draws back arrow
        backBtn.draw(restaurant.batch);
        restaurant.batch.end();
        //Draws HUD
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
