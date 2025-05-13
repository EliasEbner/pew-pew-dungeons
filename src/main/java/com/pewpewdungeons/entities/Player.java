package com.pewpewdungeons.entities;

import com.pewpewdungeons.core.Movable;
import com.pewpewdungeons.items.Inventory;
import com.pewpewdungeons.physics.RigidBody;
import com.raylib.Raylib;

public class Player extends GameObject implements Movable {
    private double health;
    private double mana;
    private RigidBody rigidBody;
    private Inventory inventory;

    @Override
    public void draw() {
    }

    @Override
    public void move() {

    }

    @Override
    public void update() {

    }
}
