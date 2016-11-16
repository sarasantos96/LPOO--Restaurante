package com.restaurant.game.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class Order {
    private Sprite order;
    private Array<RestaurantLogic.RestaurantFood> food;
    private int orderTime;
    private int numberItems;

    /**
     * Constructor of class Order
     * Creats a new random order
     */
    public Order(){
        TextureRegion orderSprite = new TextureRegion();
        Random r = new Random();
        int min = 0;
        int max= 6;

        food = new Array<RestaurantLogic.RestaurantFood>();
        orderTime=0;

        //Chooses randomly a new order
        int randomOrder = r.nextInt(max - min + 1) + min;
        switch(randomOrder){
            case 0:
                orderSprite = new TextureRegion(new Texture("order_0.png"),0,0,600,230);
                food.add(RestaurantLogic.RestaurantFood.BURGER);
                food.add(RestaurantLogic.RestaurantFood.FRIES);
                food.add(RestaurantLogic.RestaurantFood.DRINK);
                break;
            case 1:
                orderSprite = new TextureRegion(new Texture("order_1.png"),0,0,600,230);
                food.add(RestaurantLogic.RestaurantFood.BURGER);
                food.add(RestaurantLogic.RestaurantFood.BURGER);
                food.add(RestaurantLogic.RestaurantFood.DRINK);
                break;
            case 2:
                orderSprite = new TextureRegion(new Texture("order_2.png"),0,0,600,230);
                food.add(RestaurantLogic.RestaurantFood.DRINK);
                food.add(RestaurantLogic.RestaurantFood.FRIES);
                break;
            case 3:
                orderSprite = new TextureRegion(new Texture("order_3.png"),0,0,600,230);
                food.add(RestaurantLogic.RestaurantFood.FRIES);
                food.add(RestaurantLogic.RestaurantFood.FRIES);
                break;
            case 4:
                orderSprite = new TextureRegion(new Texture("order_4.png"),0,0,600,230);
                food.add(RestaurantLogic.RestaurantFood.BURGER);
                food.add(RestaurantLogic.RestaurantFood.DRINK);
                break;
            case 5:
                orderSprite = new TextureRegion(new Texture("order_5.png"),0,0,600,230);
                food.add(RestaurantLogic.RestaurantFood.DRINK);
                break;
            case 6:
                orderSprite = new TextureRegion(new Texture("order_6.png"),0,0,600,230);
                food.add(RestaurantLogic.RestaurantFood.BURGER);
                food.add(RestaurantLogic.RestaurantFood.FRIES);
                food.add(RestaurantLogic.RestaurantFood.FRIES);
                food.add(RestaurantLogic.RestaurantFood.DRINK);
                break;
        }

        numberItems = food.size;

        order = new Sprite();
        order.setBounds(0,0,225,100);
        order.setRegion(orderSprite);
    }

    /**
     * Return the Sprite of the order
     * @return
     */
    public Sprite getSpriteOrder(){
        return this.order;
    }

    /**
     * Removes a food from the order if it is in the order
     * @param food food being served
     * @return
     */
    public boolean serve(RestaurantLogic.RestaurantFood food){
        int i;
        boolean serve = false;
        //Checks if food being served is part of the order
        for(i = 0; i < this.food.size ; i++){
            if(this.food.get(i) == food){
                this.food.removeIndex(i);
                serve = true;
                break;
            }
        }
        return serve;
    }

    /**
     * Checks if order is complete
     * @return true if it is complete, false otherwise
     */
    public boolean isComplete(){
        if(this.food.size == 0)
            return true;

        return false;
    }

    /**
     * Increases the time of serving the order
     */
    public void increaseTime(){
        orderTime++;
    }

    /**
     * Returns the score of the order according to the time took by serving it completely
     * @return score
     */
    public int getScore(){
        int orderScore = RestaurantLogic.SCORE_VALUE / orderTime;

        return orderScore;
    }

    /**
     * Returns the money of the order according to the number of food served
     * @return money
     */
    public int getMoney(){
        int moneyOrder =  RestaurantLogic.FOOD_PRICE * numberItems / 3;

        return moneyOrder;
    }
}