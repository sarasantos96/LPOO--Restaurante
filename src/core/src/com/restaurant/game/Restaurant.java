package com.restaurant.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.restaurant.game.Tools.ScoresManager;
import com.restaurant.game.graphics.GameOver;
import com.restaurant.game.graphics.MainMenu;
import com.restaurant.game.graphics.PlayScreen;
import com.restaurant.game.graphics.Scores;
import com.restaurant.game.graphics.Store;
import com.restaurant.game.logic.RestaurantLogic;

public class Restaurant extends Game {
	public final static int WIDTH = 1210;
	public final static int HEIGHT = 680;
	public SpriteBatch batch;
	public RestaurantLogic logic;
	public World world;

	//Game Screens
	public MainMenu mainMenu;
	public PlayScreen playScreen;
	public GameOver gameOverScreen;
	public Store storeScreen;
	public ScoresManager scoresManager;
	public Scores scoresScreen;

	//Game Music
	public Music mainTheme;
	public Music playMusic;
	public Music orderSoundEffect;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		world = new World(new Vector2(0,0), true);
		logic = new RestaurantLogic(this);

		mainTheme = Gdx.audio.newMusic(Gdx.files.internal("spongebob_song.mp3"));
		playMusic = Gdx.audio.newMusic(Gdx.files.internal("play_song.mp3"));
		orderSoundEffect = Gdx.audio.newMusic(Gdx.files.internal("complete_order_effect.mp3"));

		scoresManager = new ScoresManager();
		mainMenu = new MainMenu(this);
		storeScreen = new Store(this);
		scoresScreen = new Scores(this);

		setScreen(mainMenu);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
}
