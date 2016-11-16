package com.restaurant.game.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.restaurant.game.Restaurant;

public class Machine extends Sprite{
    //Upgrade Level
    public enum MachineUpgrade {UPGRADE1, UPGRADE2, UPGRADE3}
    //Machine State
    public enum MachineState {SLEEPING, PRODUCING};
    //Position variables
    private Restaurant restaurant;
    private int width;
    private int height;
    private float x;
    private float y;
    private int produtionTime;                     //Amount of time to produce a product
    private RestaurantLogic.RestaurantFood food;   //Type of food production
    private MachineUpgrade upgrade;                //Current upgrade level
    private MachineState state;                    //Current State
    private MachineState previousState;            //Previous State
    private float stateTime;                       //State counter
    private int time;                              //Production counter
    private int numberFood;

    //Textures and Animations
    private Sprite cookButton;
    private Animation loading;
    private TextureRegion machine;

    /**
     * Constructor of class Machine
     * @param restaurant
     * @param x
     * @param y
     * @param width
     * @param heigth
     * @param spriteName
     * @param animation
     * @param food
     */
    public Machine(Restaurant restaurant, float x, float y, int width, int heigth, String spriteName, String animation, RestaurantLogic.RestaurantFood food){
        this.restaurant = restaurant;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = heigth;
        this.food = food;

        upgrade = MachineUpgrade.UPGRADE1;
        state = MachineState.SLEEPING;
        previousState = MachineState.SLEEPING;
        this.produtionTime = 10 * 60;
        this.time = produtionTime;
        this.stateTime = 0;


        this.numberFood = 2;

        machine = new TextureRegion(new Texture(spriteName),0,0,200,193);
        setBounds(x - (this.width/2) ,y - (height /2),this.width,this.height);
        setRegion(machine);

        TextureRegion cook = new TextureRegion(new Texture("cook_button.png"),0,0,600,192);
        cookButton = new Sprite();
        cookButton.setBounds(x - (this.width/2) ,y - (height /2)- 50,100,50);
        cookButton.setRegion(cook);

        //Load Animation
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i=0; i < 8; i++) {
            TextureRegion region = new TextureRegion(new Texture(animation), i * 200, 0, 200, 193);
            frames.add(region);
        }
        loading = new Animation(0.1f, frames);
    }

    /**
     * Returns the currect frame
     * @param dt current time
     * @return texture of current frame
     */
    public TextureRegion getFrame(float dt){
        TextureRegion region ;

        switch(state){
            case PRODUCING:
                region = loading.getKeyFrame(stateTime, true);
                break;
            case SLEEPING:
            default:
                region = machine;
        }

        stateTime = previousState == state ? stateTime + dt : 0;
        previousState = state;

        return region;
    }

    /**
     * Updates the number of food produced by the machine
     */
    public void updateNumberFood(){
        switch(food){
            case BURGER:
                restaurant.logic.setNumBurgers(numberFood);
                break;
            case FRIES:
                restaurant.logic.setNumFries(numberFood);
                break;
            case DRINK:
                restaurant.logic.setNumDrinks(numberFood);
                break;
        }
    }

    /**
     * Updates machine
     * @param dt current time
     */
    public void update(float dt){

        if(state == MachineState.PRODUCING){
            if(time == 0){
                state = MachineState.SLEEPING;
                numberFood++;
                updateNumberFood();
                time = produtionTime;
            }else{
                time--;
            }
        }
        setRegion(getFrame(dt));
    }

    /**
     * Checks if cook button has been pressed
     * @param x x coordinate
     * @param y y coordinate
     * @return
     */
    public boolean beginCook(float x, float y){
        boolean t ;

        Rectangle rect = cookButton.getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Checks if machine has been touched
     * @param x x coordinate
     * @param y y coordinate
     * @return
     */
    public boolean isTouch(float x, float y){
        boolean t;

        Rectangle rect = getBoundingRectangle();
        t = rect.contains(x,y);

        return t;
    }

    /**
     * Checks if the food produce by the machine is in the client order, seving him if so
     * @param client
     */
    public void serveClient(Client client){
        if(numberFood > 0) {
           if(client.getOrder().serve(food)) {
               numberFood--;
           }
            updateNumberFood();
        }
    }

    /**
     * Modifies the current state to Producing
     */
    public void setProducing(){
        this.state = MachineState.PRODUCING;
    }

    /**
     * Returns the cook button of the machine
     * @return
     */
    public Sprite getCookButton(){
        return cookButton;
    }

    /**
     * Updates the machine upgrade to the next level
     */
    public void upgradeMachine(){
        if(upgrade == MachineUpgrade.UPGRADE1){
            upgrade = MachineUpgrade.UPGRADE2;
            produtionTime = 5 * 60;
        }else if(upgrade == MachineUpgrade.UPGRADE2){
            upgrade = MachineUpgrade.UPGRADE3;
            produtionTime = 2 * 60;
        }
    }

    /**
     * Returns the current upgrade level
     * @return upgrade
     */
    public MachineUpgrade getUpgrade() {
        return upgrade;
    }

    /**
     * Returns the current production time
     * @return productionTime
     */
    public int getProdutionTime() {
        return produtionTime;
    }
}
