package com.pewpewdungeons.entities;

import com.pewpewdungeons.core.AutoMovable;
import com.pewpewdungeons.items.inventory.Inventory;

public class Enemy extends GameObject implements AutoMovable {

    private double health;
    private Inventory inventory;

    @Override
    public void autoMove() {}

    @Override
    public void draw() {}

    @Override
    public void update(float dt) {}
}
