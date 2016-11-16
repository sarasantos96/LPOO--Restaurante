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
import com.restaurant.game.graphics.GameOver;

/**
 * Created by Sara on 29/05/2016.
 */
public class GameOverHUD {
    private int numscore;               //Score value
    private Label score;                //Score value Label
    private Label scoreLabel;           //Score Label

    public Stage stage;
    private Viewport viewport;

    /**
     * Constructor of class GameOverHUD
     * @param sb
     * @param numscore
     */
    public GameOverHUD(SpriteBatch sb, int numscore){
        viewport = new FitViewport(GameOver.GAMEOVER_WIDTH,GameOver.GAMEOVER_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        this.numscore = numscore;

        scoreLabel = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        score = new Label(String.format("%06d",numscore), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        scoreLabel.setFontScale(2,2);
        score.setFontScale(2,2);

        Table table = new Table();
        table.left();
        table.top();
        table.setFillParent(true);

        table.add(scoreLabel).padTop(100).padLeft(190);
        table.add(score).padTop(100).padLeft(50);
        stage.addActor(table);
    }

}
