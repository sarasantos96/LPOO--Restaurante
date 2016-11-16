package com.restaurant.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoresManager {
   private static Preferences pref; //Preferences File

    /**
     * Constructor of class ScoresManager
     */
    public ScoresManager(){
        //If the preferences file doesn't exit creates it and sets default values
        pref = Gdx.app.getPreferences("HighScores");
        if(!pref.contains("first")){
            pref.putInteger("first",0);
        }
        if(!pref.contains("second")){
            pref.putInteger("second",0);
        }
        if(!pref.contains("third")){
            pref.putInteger("third",0);
        }
        pref.flush();
    }

    /**
     * Returns the first high score
     * @return first
     */
    public Integer getFirst(){
        return pref.getInteger("first");
    }

    /**
     * Returns the second high score
     * @return second
     */
    public Integer getSecond(){
        return pref.getInteger("second");
    }

    /**
     * Returns the third high score
     * @return third
     */
    public Integer getThird(){
        return pref.getInteger("third");
    }

    /**
     * Checks if a given score is a new high score
     * If it is a high scores updates the scores file
     * @param score
     * @return true if it's high score, false otherwise
     */
    public boolean updateHighScores(int score){
        boolean highScore = false;

        if(score > pref.getInteger("first")){
            highScore = true;
            pref.putInteger("third", getSecond());
            pref.putInteger("second", getFirst());
            pref.putInteger("first", score);
        }else if(score > pref.getInteger("second")){
            highScore = true;
            pref.putInteger("third", getSecond());
            pref.putInteger("second", score);
        }else if(score > pref.getInteger("third")){
            highScore = true;
            pref.putInteger("third", score);
        }

        pref.flush();
        return highScore;
    }

    public boolean isFirstscore(int score){
        boolean b = false;
        if(score > getFirst())
            b= true;

        return b;
    }
}
