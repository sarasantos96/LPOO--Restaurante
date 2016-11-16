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
import com.restaurant.game.graphics.Scores;


public class ScoresHUD {
    private int firstscore;
    private int secondscore;
    private int thirdscore;
    private Label first;    //First high score
    private Label second;   //Second high score
    private Label third;    //Third high score

    public Stage stage;
    private Viewport viewport;

    /**
     * Constructor of ScoresHUD class
     * @param sb
     * @param first
     * @param second
     * @param third
     */
    public ScoresHUD(SpriteBatch sb,int first, int second, int third){
        viewport = new FitViewport(Scores.SCORES_WIDTH,Scores.SCORES_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        this.firstscore = first;
        this.secondscore = second;
        this.thirdscore = third;

        this.first = new Label(String.format("%06d",firstscore), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.second = new Label(String.format("%06d",secondscore), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        this.third = new Label(String.format("%06d",thirdscore), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        this.first.setFontScale(3,3);
        this.second.setFontScale(3,3);
        this.third.setFontScale(3,3);

        Table table = new Table();
        table.top();
        table.left();
        table.setFillParent(true);

        table.add(this.first).padTop(130).padLeft(300);
        table.row();
        table.add(this.second).padTop(50).padLeft(300);
        table.row();
        table.add(this.third).padTop(50).padLeft(300);

        stage.addActor(table);
    }

    public void setFirst(int first){
        this.firstscore = first;
        this.first.setText(String.format("%06d",firstscore));
    }
    public void setSecond(int second){
        this.secondscore = second;
        this.second.setText(String.format("%06d",secondscore));
    }
    public void setThird(int third){
        this.thirdscore = third;
        this.third.setText(String.format("%06d",thirdscore));
    }
}
