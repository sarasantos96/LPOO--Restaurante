package com.restaurant.game.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.restaurant.game.Restaurant;
import com.restaurant.game.Tools.Box2DCreator;

public class Client extends Sprite {
    //Client State
    public enum ClientState {WAITING, ARRIVING, LEAVING, MOVING_IN_LINE, NOT_PRESENT};
    private Restaurant restaurant;
    //line variables
    private int wait;
    private float linePosition;
    private boolean isServed;

    //Client variables
    private ClientState state;
    private ClientState previousState;
    private Order order;

    //Box2D variables
    private Box2DCreator box2DCreator;
    private Body body;

    //Animations
    private float stateTime;
    private TextureRegion clientStanding;
    private Animation clientArriving;
    private Animation clientLeaving;

    /**
     * Constructor of class Client
     * @param restaurant
     */
    public Client(Restaurant restaurant) {
        super(new Texture("squidward_standing.png"));

        this.restaurant = restaurant;
        this.order = new Order();

        //Creates a new Box2D body
        box2DCreator = new Box2DCreator(restaurant.world);
        body = box2DCreator.createBody(Restaurant.WIDTH - 39,295,39);

        state = ClientState.ARRIVING;
        previousState = ClientState.ARRIVING;
        wait = 0;
        linePosition = restaurant.logic.getLineSize();
        stateTime=0;
        isServed = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Initialize arriving animation
        for(int i=0; i < 7; i++)
            frames.add(new TextureRegion(new Texture("squidward_arriving.png"), i * 80, 0, 80,165));
        clientArriving = new Animation(0.1f, frames);
        frames.clear();

        //Initialize leaving animation
        for(int i=0; i < 5; i++)
            frames.add(new TextureRegion(new Texture("squidward_leaving.png"), i * 80, 0, 80,165));
        clientLeaving = new Animation(0.15f, frames);
        frames.clear();

        //Initialize standing texture
        clientStanding = new TextureRegion(new Texture("squidward_standing.png"),0,0,80,165);
        setBounds(body.getPosition().x - (getWidth()/2),body.getPosition().y- (getHeight()/2),53,100);
        setRegion(clientStanding);
    }

    /**
     * Updates client state making him move foward when other client leaves
     */
    public void moveFowardInLine(){
        if(state == ClientState.ARRIVING)
            linePosition -= 2*42;
         if(state == ClientState.WAITING) {
             state = ClientState.MOVING_IN_LINE;
             linePosition -= 2*42;
         }
    }

    /**
     * Returns the currect frame
     * @param dt current time
     * @return texture of current frame
     */
    public TextureRegion getFrame(float dt){
        TextureRegion region ;

        switch(state){
            case ARRIVING:
            case MOVING_IN_LINE:
                region = clientArriving.getKeyFrame(stateTime, true);
                break;
            case LEAVING:
                region = clientLeaving.getKeyFrame(stateTime,true);
                break;
            case WAITING:
            default:
                region = clientStanding;
        }

        if(!region.isFlipX())
            region.flip(true,false);

        stateTime = previousState == state ? stateTime + dt : 0;
        previousState = state;

        return region;
    }

    /**
     * Updates flags and movement of client
     * If leaving or arriving updates coordinates
     * If waiting checks if his order is complete
     * @param dt  current time
     */
    public void updateMovement(float dt){
        setRegion(getFrame(dt));
        switch (state){
            case ARRIVING:
                if(body.getPosition().x >= linePosition)
                    body.setTransform(body.getPosition().x -3,body.getPosition().y,body.getAngle());
                else
                    state = ClientState.WAITING;

                break;

            case LEAVING:
                if(body.getPosition().y <= Restaurant.HEIGHT)
                    body.setTransform(body.getPosition().x, body.getPosition().y + 5, body.getAngle());
                else
                    state = ClientState.NOT_PRESENT;

                break;

            case WAITING:
                    if(isServed) {
                        restaurant.orderSoundEffect.play();
                        state = ClientState.LEAVING;
                    }
                break;
            case MOVING_IN_LINE:
                if(body.getPosition().x >= linePosition)
                    body.setTransform(body.getPosition().x -5,body.getPosition().y,body.getAngle());
                else
                    state = ClientState.WAITING;
                break;
            default:
                break;
        }
        setPosition(body.getPosition().x - (getWidth()/2),body.getPosition().y- (getHeight()/2));
    }

    /**
     * Updates client
     * If client order is served updates score and money
     * @param dt
     */
    public void update(float dt){
        isServed = order.isComplete();
        if(isServed){
            int scoreInc = order.getScore();
            int moneyInc = order.getMoney();

            restaurant.logic.increaseScore(scoreInc);
            restaurant.logic.increaseMoney(moneyInc);
        }
        order.increaseTime();
        updateMovement(dt);
    }

    /**
     * Discarts player
     * Function used to eliminate client when he leaves
     */
    public void dispose(){
        restaurant.playScreen.getWorld().destroyBody(body);
    }

    /**
     * Returns the current client state
     * @return state
     */
    public ClientState getState(){
        return state;
    }

    /**
     * Return the sprite of the client order
     */
    public Sprite getSpriteOrder(){
        return order.getSpriteOrder();
    }

    /**
     * Returns client order
     */
    public Order getOrder(){
        return order;
    }
}
