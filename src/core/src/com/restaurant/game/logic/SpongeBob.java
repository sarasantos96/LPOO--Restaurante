package com.restaurant.game.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.restaurant.game.Restaurant;
import com.restaurant.game.Tools.Box2DCreator;

public class SpongeBob extends Sprite {
    //SpongeBob state
    public enum SpongeState {STANDING, WALKING_LEFT, WALKING_RIGHT}
    private Restaurant restaurant;
    private SpongeState state;
    private SpongeState previousState;
    public Body body;
    private Box2DCreator box2DCreator;

    //sprites and animations
    private float stateTime;
    private TextureRegion spongeStanding;
    private Animation spongeWalking;
    private boolean rightDirection;

    /**
     * Constructor of class SpongeBob
     * @param restaurant
     */
    public SpongeBob(Restaurant restaurant) {
        super(new Texture("spongebob_standing.png"));
        this.restaurant = restaurant;
        state = SpongeState.STANDING;
        previousState = SpongeState.STANDING;
        stateTime = 0;
        rightDirection = true;

        box2DCreator = new Box2DCreator(restaurant.world);
        body = box2DCreator.createBody(100, 105, 50);

        //initialize walking animation
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i < 8; i++)
            frames.add(new TextureRegion(new Texture("spongebob_walking.png"), i * 80, 0, 80,80));
        spongeWalking = new Animation(0.1f, frames);

        //initialize standing texture
        spongeStanding = new TextureRegion(new Texture("spongebob_standing.png"),0,0,39,39);
        setBounds(body.getPosition().x - (getWidth()/2),body.getPosition().y- (getHeight()/2),80,80);
        setRegion(spongeStanding);

    }

    /**
     * Returns the currect frame
     * @param dt current time
     * @return texture of current frame
     */
    public TextureRegion getFrame(float dt){
        TextureRegion region ;

        switch(state){
            case WALKING_RIGHT:
                region = spongeWalking.getKeyFrame(stateTime, true);
                break;
            case WALKING_LEFT:
                region = spongeWalking.getKeyFrame(stateTime,true);
                break;
            case STANDING:
            default:
                   region = spongeStanding;
        }

        if( !rightDirection && !region.isFlipX())
            region.flip(true,false);
        else if(rightDirection && region.isFlipX())
            region.flip(true,false);

        stateTime = previousState == state ? stateTime + dt : 0;
        previousState = state;

        return region;
    }

    /**
     * Updates spongebob position and frame according to it's frame
     * @param dt
     */
    public void updateMovement(float dt) {
        setRegion(getFrame(dt));
        if(state == SpongeState.WALKING_RIGHT){
            body.setTransform(new Vector2(body.getPosition().x +(5), body.getPosition().y),body.getAngle());
        }
        if(state == SpongeState.WALKING_LEFT){
            body.setTransform(new Vector2(body.getPosition().x -(5), body.getPosition().y),body.getAngle());
        }

        setPosition(body.getPosition().x - (getWidth()/2),body.getPosition().y- (getHeight()/2));
    }

    /**
     * Checks if spongebob should move right
     * @param x x coordinate
     * @return
     */
    public boolean moveRight(float x){
        if(x >= body.getPosition().x)
            return true;

        return false;
    }

    /**
     * Checks if spongebob should move left
     * @param x x coordinate
     * @return
     */
    public boolean moveLeft(float x){
        if(x <= body.getPosition().x)
            return true;

        return false;
    }

    /**
     * Returns spongebob state
     * @return
     */
    public SpongeState getState(){
        return state;
    }

    /**
     * Modifies spongebob state
     * @param state
     */
    public void setState(SpongeState state){
        this.state = state;
    }

    /**
     * Sets moving right flag
     * Necessary for knowing when to flip the animation
     * @param direction
     */
    public void setRightDirection(boolean direction){
        this.rightDirection = direction;
    }

    /**
     * Updates spongebob state
     * @param x
     * @param y
     */
    public void updateState(float x, float y){
        if(body.getPosition().x >= x && (getState() == SpongeState.WALKING_RIGHT)) {
            setState(SpongeState.STANDING);
        }
        if(body.getPosition().x <= x && (getState() == SpongeState.WALKING_LEFT)) {
            setState(SpongeState.STANDING);
        }
    }

    /**
     * Updates movement flags
     * @param touchPos
     */
    public void updateFlags(float touchPos){
        if (moveRight(touchPos)) {
            setState(SpongeState.WALKING_RIGHT);
            setRightDirection(true);
        }

        if (moveLeft(touchPos)) {
            setState(SpongeState.WALKING_LEFT);
            setRightDirection(false);
        }
    }
}
