package com.pewpewdungeons.entities;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.core.Movable;
import com.pewpewdungeons.items.RangeWeapon;
import com.pewpewdungeons.items.inventory.PlayerInventory;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.physics.rigidBody.RecRigidBody;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import java.util.ArrayList;

import static com.raylib.Raylib.*;

public class Player extends GameObject implements Movable {

    private double health;
    private double mana;
    private RecRigidBody rigidBody;
    private PlayerInventory inventory;

    public Player(double health, double mana, Vector2 position, Vector2 size, float speed) {
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
        float dx = 0;
        float dy = 0;
        float v = this.speed * Raylib.GetFrameTime();

        if (Raylib.IsKeyDown(KEY_A)) dx -= v;
        if (Raylib.IsKeyDown(KEY_D)) dx += v;

        if (Raylib.IsKeyDown(KEY_W)) dy -= v;
        if (Raylib.IsKeyDown(KEY_S)) dy += v;

        this.position.add(dx, dy);
    }

    @Override
    public void update() {
        this.move();
    }
}
