package com.pewpewdungeons.entities;

import com.pewpewdungeons.core.AutoMovable;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.physics.rigidBody.RigidBody;

public class Projectile extends GameObject implements AutoMovable {

    private Vector2 direction;
    private RigidBody rigidBody;

    @Override
    public void autoMove() {}

    @Override
    public void draw() {}

    @Override
    public void update() {}
}
