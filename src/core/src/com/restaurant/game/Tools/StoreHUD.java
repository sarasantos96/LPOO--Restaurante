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
import com.restaurant.game.graphics.GameOver;

public class StoreHUD {
    private int numMoney;       //Amount of money
    private Label money;        //Amount of money label
    private Label moneyLabel;   //Money label

    public Stage stage;
    private Viewport viewport;

    /**
     * Constructor of class StoreHUD
     * @param sb
     * @param numMoney
     */
    public StoreHUD(SpriteBatch sb, int numMoney){
        viewport = new FitViewport(GameOver.GAMEOVER_WIDTH,GameOver.GAMEOVER_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        this.numMoney = numMoney;

        moneyLabel = new Label("Money", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        money = new Label(String.format("%05d",numMoney), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        moneyLabel.setFontScale(2,2);
        money.setFontScale(2,2);

        Table table = new Table();
        table.bottom();
        table.right();
        table.setFillParent(true);

        table.add(moneyLabel).padBottom(10).padRight(40);
        table.add(money).padBottom(10).padRight(180);

        stage.addActor(table);
    }

    /**
     * Modifies the amount of money
     * @param money
     */
    public void setStoreHUDMoney(int money){
        this.numMoney = money;
        this.money.setText(String.format("%05d",this.numMoney));
    }
}
