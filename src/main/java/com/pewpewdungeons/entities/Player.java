package com.pewpewdungeons.entities;

import static com.raylib.Raylib.KEY_A;
import static com.raylib.Raylib.KEY_D;
import static com.raylib.Raylib.KEY_S;
import static com.raylib.Raylib.KEY_W;

import com.pewpewdungeons.core.Movable;
import com.pewpewdungeons.items.RangeWeapon;
import com.pewpewdungeons.items.inventory.Inventory;
import com.pewpewdungeons.items.inventory.PlayerInventory;
import com.pewpewdungeons.physics.Vector2;
import com.pewpewdungeons.physics.rigidBody.RecRigidBody;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import java.util.ArrayList;

public class Player extends GameObject implements Movable {

    private double health;
    private double mana;
    private RecRigidBody rigidBody;
    private PlayerInventory inventory;

    public Player(double health, double mana, Vector2 position, Vector2 size, int speed) {
        this.health = health;
        this.mana = mana;
        this.position = position;
        this.size = size;
        this.speed = speed;
        this.rigidBody = new RecRigidBody(position, size);
        this.inventory = new PlayerInventory(new ArrayList<RangeWeapon>(), 0);
    }

    @Override
    public void draw() {
        try (Raylib.Vector2 raylibPositionVector = this.position.toNative()) {
            Raylib.DrawRectangleV(raylibPositionVector, this.size.toNative(), Jaylib.RED);
        }
    }

    @Override
    public void move() {
        if (Raylib.IsKeyDown(KEY_A)) {
            this.position.sub(this.speed * Raylib.GetFrameTime(), 0);
        } else if (Raylib.IsKeyDown(KEY_D)) {
            this.position.add(this.speed * Raylib.GetFrameTime(), 0);
        }

        if (Raylib.IsKeyDown(KEY_W)) {
            this.position.sub(0, this.speed * Raylib.GetFrameTime());
        } else if (Raylib.IsKeyDown(KEY_S)) {
            this.position.add(0, this.speed * Raylib.GetFrameTime());
        }
    }

    @Override
    public void update() {
        this.move();
    }
}
