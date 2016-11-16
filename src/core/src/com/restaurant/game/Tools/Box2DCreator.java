package com.restaurant.game.Tools;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.restaurant.game.Restaurant;

public class Box2DCreator {
    private World world;

    /**
     * Constructor of class Box2DCreator
     * @param world
     */
    public Box2DCreator(World world){
        this.world = world;
    }

    /**
     * Creates a new body for the ground
     */
    public void createGround(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(100,100);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Restaurant.WIDTH - 450,5);
        fdef.shape = shape;
        body.createFixture(fdef);

    }

    /**
     * Creates a new body for the counter where clients wait
     */
    public void createCounter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(100, 270);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Restaurant.WIDTH,25);
        fdef.shape = shape;
        body.createFixture(fdef);
    }

    /**
     * Creates a new body with the given properties
     * @param x
     * @param y
     * @param radius
     * @return
     */
    public Body createBody(float x, float y, float radius){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x ,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fdef.shape =shape;
        body.createFixture(fdef);

        return body;
    }
}
