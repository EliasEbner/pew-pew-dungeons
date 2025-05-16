package com.pewpewdungeons.physics.rigidBody;

import com.pewpewdungeons.physics.Collidable;
import com.pewpewdungeons.physics.Vector2;

public abstract class RigidBody implements Collidable {

    protected Vector2 position;
    protected Vector2 size;

    public RigidBody(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }
}
