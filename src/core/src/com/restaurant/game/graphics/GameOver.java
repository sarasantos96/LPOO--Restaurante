package com.restaurant.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.restaurant.game.Restaurant;
import com.restaurant.game.Tools.GameOverHUD;
import com.restaurant.game.logic.RestaurantLogic;

public class GameOver implements Screen {
    //Screen Dimensions
    public final static int GAMEOVER_WIDTH = 592;
    public final static int GAMEOVER_HEIGHT = 364;
    //Camera and Viewport
    private OrthographicCamera cam;
    private Viewport viewport;
    //GameOver Variables
    private Restaurant restaurant;
    private RestaurantLogic restaurantLogic;
    private boolean highScore;
    private Texture background;
    private GameOverHUD hud;
    private Sprite hScore;

    /**
     * Constructor of class GameOver
     * Creates a new Game Over screen showing the score of the game
     * @param restaurant
     * @param restaurantLogic
     */
    public GameOver(Restaurant restaurant, RestaurantLogic restaurantLogic){
        this.restaurant = restaurant;
        this.restaurantLogic = restaurantLogic;

        //Initializes screen camera
        cam = new OrthographicCamera();
       // cam.setToOrtho(false, GAMEOVER_WIDTH,GAMEOVER_HEIGHT);
        viewport = new StretchViewport(GAMEOVER_WIDTH, GAMEOVER_HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth() /2, viewport.getWorldHeight()/2, 0);
        viewport.apply();

        //Screen Texture
        background = new Texture("gameover.png");

        //Initializes GameOverHud for showing the player's score
        hud = new GameOverHUD(restaurant.batch, restaurantLogic.getScore());
        this.highScore = restaurant.scoresManager.isFirstscore(restaurantLogic.getScore());
        restaurant.scoresManager.updateHighScores(restaurantLogic.getScore());
        this.restaurant.scoresScreen.updateScores();
        System.out.println(restaurant.scoresManager.getFirst());
        System.out.println(restaurant.scoresManager.getSecond());
        System.out.println(restaurant.scoresManager.getThird());

        //Play background music
        restaurant.mainTheme.play();
        restaurant.mainTheme.setLooping(true);

        Texture hs = new Texture("high_score.png");
        hScore = new Sprite();
        hScore.setBounds(250,185,100,50);
        hScore.setRegion(hs);
    }

    /**
     * Handles the screen input
     * If the screen is touch return to the Main Menu
     */
    public void handleInput() {
        if(Gdx.input.justTouched()){
                this.dispose();
                restaurant.logic = new RestaurantLogic(restaurant);
                restaurant.world = new World(new Vector2(0,0), true);
                restaurant.setScreen(restaurant.mainMenu);
        }
    }

    /**
     * Updates Game Over Screen
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
        if(highScore)
            hScore.draw(restaurant.batch);
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
