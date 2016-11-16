package com.restaurant.game.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.restaurant.game.Restaurant;
import com.restaurant.game.logic.RestaurantLogic;

/**
 * Created by Sara on 26/05/2016.
 */
public class HUD {
    private RestaurantLogic logic;

    private int score;                  //score value
    private int money;                  //amount money
    private int numberBurgers;
    private int numberFries;
    private int numberDrinks;

    private Label scoreLabel;           //Scores Label
    private Label moneyLabel;           //Money Label
    private Label burgerLabel;          //Number of burgers Label
    private Label friesLabel;           //Number of fries Label
    private Label drinkLabel;           //Number of drinks Label
    private Label sLabel;               //Score value Label
    private Label mLabel;               //Amount money Label

    public Stage stage;
    private Viewport viewport;

    /**
     * Constructor of class HUD
     * @param logic
     * @param sb
     * @param score
     * @param money
     * @param numberBurgers
     * @param numberFries
     * @param numberDrinks
     */
    public HUD(RestaurantLogic logic,SpriteBatch sb, int score, int money, int numberBurgers, int numberFries, int numberDrinks){
        this.logic = logic;

        viewport = new FitViewport(Restaurant.WIDTH, Restaurant.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        this.score = score;
        this.money = money;
        this.numberBurgers = numberBurgers;
        this.numberDrinks = numberDrinks;
        this.numberFries = numberFries;

        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        sLabel = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        moneyLabel = new Label(String.format("%05d",money), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mLabel = new Label("Money", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        burgerLabel = new Label(String.format("%02d",numberBurgers), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        friesLabel = new Label(String.format("%02d",numberFries), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        drinkLabel = new Label(String.format("%02d",numberDrinks), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreLabel.setFontScale(3,3);
        sLabel.setFontScale(3,3);
        moneyLabel.setFontScale(3,3);
        mLabel.setFontScale(3,3);
        burgerLabel.setFontScale(3,3);
        friesLabel.setFontScale(3,3);
        drinkLabel.setFontScale(3,3);

        Table topTable = new Table();

        topTable.left();
        topTable.top();
        topTable.setFillParent(true);

        topTable.add(sLabel).padTop(17);
        topTable.add(scoreLabel).padLeft(50).padTop(17);
        topTable.add(mLabel).padLeft(50).padTop(17);
        topTable.add(moneyLabel).padLeft(50).padTop(17);

        Table bottomTable = new Table();
        bottomTable.bottom();
        bottomTable.right();
        bottomTable.setFillParent(true);

        bottomTable.add(burgerLabel).padRight(55).padBottom(180);
        bottomTable.add(friesLabel).padRight(55).padBottom(180);
        bottomTable.add(drinkLabel).padRight(55).padBottom(180);

        stage.addActor(topTable);
        stage.addActor(bottomTable);
    }

    /**
     * Returns the number of burgers
     * @return number food
     */
    public int getNumberBurgers() {
        return numberBurgers;
    }

    /**
     * Returns the number of fries
     * @return number food
     */
    public int getNumberFries() {
        return numberFries;
    }

    /**
     * Returns the number of drink
     * @return number food
     */
    public int getNumberDrinks() {
        return numberDrinks;
    }

    /**
     * Returns the score value
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the amount of money
     * @return money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Modifies the score value; updates the score value label
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
        this.scoreLabel.setText(String.format("%06d",score));
    }

    /**
     * Modifies the money value; updates the amount money label
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
        this.moneyLabel.setText(String.format("%05d",money));
    }

    /**
     *  Modifies the number of burger; updates the number of burgers label
     * @param numberBurgers
     */
    public void setNumberBurgers(int numberBurgers) {
        this.numberBurgers = numberBurgers;
        this.burgerLabel.setText(String.format("%02d",numberBurgers));
    }

    /**
     * Modifies the number of fries; updates the number of fries label
     * @param numberFries
     */
    public void setNumberFries(int numberFries) {
        this.numberFries = numberFries;
        this.friesLabel.setText(String.format("%02d",numberFries));
    }

    /**
     * Modifies the number of drinks; updates the number of drinks label
     * @param numberDrinks
     */
    public void setNumberDrinks(int numberDrinks) {
        this.numberDrinks = numberDrinks;
        this.drinkLabel.setText(String.format("%02d",numberDrinks));
    }

    /**
     * Updates all the labels
     */
    public void update(){
        setNumberBurgers(logic.getNumBurgers());
        setNumberFries(logic.getNumFries());
        setNumberDrinks(logic.getNumDrinks());
        setMoney(logic.getMoney());
        setScore(logic.getScore());
    }
}
