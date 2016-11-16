package com.restaurant.game.logic;

import com.restaurant.game.Restaurant;
import java.util.ArrayList;

public class RestaurantLogic {
    //Type of food served in the restaurant
    public enum RestaurantFood {BURGER, FRIES, DRINK}
    public static int ARRIVING_TIME = 7 * 60;
    public final static int FOOD_PRICE = 2;
    public final static int SCORE_VALUE = 2159;
    public static final int MAX_NUMBER_CLIENTS = 10;
    public final static int  UPGRADE2_PRICE = 1, UPGRADE3_PRICE = 1;

    private Restaurant restaurant;
    private ArrayList<Client> clients;      //array of clients
    private SpongeBob player;               //main character

    private float lineSize;
    private int wait;
    private int score;
    private int money;
    private int numBurgers;
    private int numFries;
    private int numDrinks;

    private Machine burgerMachine;
    private Machine friesMachine;
    private Machine drinkMachine;

    /**
     * Constructor of class RestaurantLogic
     * @param restaurant
     */
    public RestaurantLogic(Restaurant restaurant){
        this.restaurant = restaurant;
        clients = new ArrayList<Client>();
        this.lineSize = 50;
        this.wait = ARRIVING_TIME;

        this.player = new SpongeBob(restaurant);

        //Number of food initially store in the machines
        numBurgers=2;
        numFries=2;
        numDrinks=2;

        //Create Machines
        burgerMachine = new Machine(restaurant, Restaurant.WIDTH - 290, 120, 120,120,"burger_stove.png","burger_loading.png", RestaurantLogic.RestaurantFood.BURGER);
        friesMachine = new Machine(restaurant, Restaurant.WIDTH - 180, 120, 120, 120,"fries_stove.png","fries_loading.png", RestaurantLogic.RestaurantFood.FRIES);
        drinkMachine = new Machine(restaurant, Restaurant.WIDTH - 70, 120, 100, 120,"drinking_machine.png","drink_loading.png", RestaurantLogic.RestaurantFood.DRINK);

    }

    /**
     * Adds a new clients to the line
     */
    public void addClient(){
        clients.add(new Client(restaurant));
        lineSize += 42*2;
    }

    /**
     * Returns the line size
     * @return lineSize
     */
    public float getLineSize(){ //MUDAR PARA A LOGICA DO RESTAURANTE
        return lineSize;
    }

    /**
     * Moves all the clients foward int the line and updates the line size
     */
    public void moveLineFoward(){
        for(Client client : clients) {
            client.moveFowardInLine();
        }
        lineSize -= 42 * 2;
    }

    /**
     * Updates the line, removing the clients that left
     * @param dt
     */
    public void updateLine(float dt){
        if(wait == ARRIVING_TIME){
            addClient();
            wait =0;
        }
        else
            wait++;

        for(int i=0; i < clients.size(); i++) {
            if (clients.get(i).getState() == Client.ClientState.NOT_PRESENT) {
                clients.get(i).dispose();
                clients.remove(i);
                moveLineFoward();
                i--;
            } else {
                clients.get(i).update(dt);
            }
        }
    }

    /**
     * Returns the array of clients
     * @return clients
     */
    public ArrayList<Client> getClients(){
        return clients;
    }

    /**
     * Returns the first client in line
     * @return first client
     */
    public Client getFirstClient(){
        return clients.get(0);
    }

    /**
     * Checks if the line is not empty
     * @return true if there is clients, false otherwise
     */
    public boolean gotClients(){
        if(clients.size() > 0)
            return true;
        return false;
    }

    /**
     * Increases the game score
     * @param increase
     */
    public void increaseScore(int increase){
        this.score += increase;
    }

    /**
     * Increases the game money
     * @param increase
     */
    public void increaseMoney(int increase){
        this.money += increase;
    }

    /**
     * Returns the score
     * @return score
     */
    public int getScore(){
        return score;
    }

    /**
     * Checks if player lost the game (number of clients waiting in line if greater than MAX_NUMBER_CLIENTS)
     * @return true if player lost, false otherwise
     */
    public boolean lostGame(){
        if(clients.size() >= MAX_NUMBER_CLIENTS)
            return true;
        return false;
    }

    /**
     * Modifies the number os burgers
     * @param numBurgers
     */
    public void setNumBurgers(int numBurgers) {
        this.numBurgers = numBurgers;
    }

    /**
     * Modifies the number of fries
     * @param numFries
     */
    public void setNumFries(int numFries) {
        this.numFries = numFries;
    }

    /**
     * Modifies the number of drinks
     * @param numDrinks
     */
    public void setNumDrinks(int numDrinks) {
        this.numDrinks = numDrinks;
    }

    /**
     * Returns the number of burgers
     * @return
     */
    public int getNumBurgers() {
        return numBurgers;
    }

    /**
     * Returns the number of fries
     * @return
     */
    public int getNumFries() {
        return numFries;
    }

    /**
     * Returns the number of drinks
     * @return
     */
    public int getNumDrinks() {
        return numDrinks;
    }

    /**
     * Returns the amount of money
     * @return
     */
    public int getMoney() {
        return money;
    }

    /**
     * Returns the burger machine
     * @return machine
     */
    public Machine getBurgerMachine() {
        return burgerMachine;
    }

    /**
     * Returns the fries machine
     * @return machine
     */
    public Machine getFriesMachine() {
        return friesMachine;
    }

    /**
     * Returns the drink machine
     * @return machine
     */
    public Machine getDrinkMachine() {
        return drinkMachine;
    }

    /**
     * Returns the main character
     * @return player
     */
    public SpongeBob getPlayer() {
        return player;
    }

    /**
     * Updates the logic
     * @param delta
     * @param x
     * @param y
     */
    public void update(float delta, float x, float y){
        //Updates body movement and flags of player
        player.updateState(x, y);
        player.updateMovement(delta);

        //updates clients
        restaurant.logic.updateLine(delta);

        //Update Machines
        burgerMachine.update(delta);
        friesMachine.update(delta);
        drinkMachine.update(delta);

        //Increases game difficulty
        if(score > 1500)
            ARRIVING_TIME = 4 * 60;

    }

    /**
     * Purchase of a burger machine upgrade
     * Removes the money for upgrade and upgrades the machine level if possible
     */
    public void upgradeBurgerMachine(){
        switch(burgerMachine.getUpgrade()){
            case UPGRADE1:
                if(money >= UPGRADE2_PRICE){
                    money -= UPGRADE2_PRICE;
                    burgerMachine.upgradeMachine();
                    restaurant.storeScreen.getHud().setStoreHUDMoney(money);
                }
                break;
            case UPGRADE2:
                if(money >= UPGRADE3_PRICE){
                    money -= UPGRADE3_PRICE;
                    burgerMachine.upgradeMachine();
                    restaurant.storeScreen.getHud().setStoreHUDMoney(money);
                }
                break;
            case UPGRADE3:
                default:
                    break;
        }
    }

    /**
     * Purchase of a fries machine upgrade
     * Removes the money for upgrade and upgrades the machine level if possible
     */
    public void upgradeFriesMachine(){
        switch(friesMachine.getUpgrade()){
            case UPGRADE1:
                if(money >= UPGRADE2_PRICE){
                    money -= UPGRADE2_PRICE;
                    friesMachine.upgradeMachine();
                    restaurant.storeScreen.getHud().setStoreHUDMoney(money);
                }
                break;
            case UPGRADE2:
                if(money >= UPGRADE3_PRICE){
                    money -= UPGRADE3_PRICE;
                    friesMachine.upgradeMachine();
                    restaurant.storeScreen.getHud().setStoreHUDMoney(money);
                }
                break;
            case UPGRADE3:
            default:
                break;
        }
    }

    /**
     * Purchase of a drink machine upgrade
     * Removes the money for upgrade and upgrades the machine level if possible
     */
    public void upgradeDrinkMachine(){
        switch(drinkMachine.getUpgrade()){
            case UPGRADE1:
                if(money >= UPGRADE2_PRICE){
                    money -= UPGRADE2_PRICE;
                    drinkMachine.upgradeMachine();
                    restaurant.storeScreen.getHud().setStoreHUDMoney(money);
                }
                break;
            case UPGRADE2:
                if(money >= UPGRADE3_PRICE){
                    money -= UPGRADE3_PRICE;
                    drinkMachine.upgradeMachine();
                    restaurant.storeScreen.getHud().setStoreHUDMoney(money);
                }
                break;
            case UPGRADE3:
            default:
                break;
        }
    }
}
