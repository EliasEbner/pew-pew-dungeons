package com.pewpewdungeons.physics;

public abstract class RigidBody implements Collidable{
    protected Vector2 position;
    protected Vector2 size;

    @Override
    public void collidesWith(RigidBody body) {

    }
}
