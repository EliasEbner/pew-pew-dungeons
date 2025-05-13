package com.pewpewdungeons.entities;

import com.pewpewdungeons.core.AutoMovable;
import com.pewpewdungeons.items.Inventory;
import com.pewpewdungeons.physics.RigidBody;
import com.raylib.Raylib;

public class Enemy extends GameObject implements AutoMovable {
    private double health;
    private Inventory inventory;
    private RigidBody rigidBody;

    @Override
    public void autoMove() {

    }

    @Override
    public void draw() {

    }

    @Override
    public void update() {

    }
}
