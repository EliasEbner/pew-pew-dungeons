package com.pewpewdungeons.entities;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.core.Movable;
import com.pewpewdungeons.items.Gun;
import com.pewpewdungeons.items.RangeWeapon;
import com.pewpewdungeons.items.inventory.PlayerInventory;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.physics.rigidBody.RecRigidBody;
import com.pewpewdungeons.projectiles.BulletProjectile;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import java.util.ArrayList;

import static com.raylib.Raylib.*;

public class Player extends GameObject implements Movable {

    private double health;
    private double mana;
    private RecRigidBody rigidBody;
    private PlayerInventory inventory;
    private RangeWeapon equippedWeapon = new Gun(new Vector2(0.25f, 0.5f), 1.2f, this);

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
        try (Raylib.Vector2 rPosition = this.position.toNative()) {
            Raylib.DrawRectangleV(rPosition, this.size.toNative(), Jaylib.RED);
        }
        if (equippedWeapon != null)
            if (equippedWeapon instanceof Drawable d) d.draw();
    }

    @Override
    public void move(float dt) {
        float dx = 0;
        float dy = 0;
        float v = this.speed * Raylib.GetFrameTime();

        if (Raylib.IsKeyDown(KEY_A)) dx -= v;
        if (Raylib.IsKeyDown(KEY_D)) dx += v;

        if (Raylib.IsKeyDown(KEY_W)) dy -= v;
        if (Raylib.IsKeyDown(KEY_S)) dy += v;

        this.position.add(dx, dy);

        if (Raylib.IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
            equippedWeapon.shoot();
        }
    }

    @Override
    public void update(float dt) {
        this.move(dt);
        if (equippedWeapon != null)
            if (equippedWeapon instanceof Updatable u) u.update(dt);
    }
}
