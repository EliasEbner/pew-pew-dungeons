package com.pewpewdungeons.entities;

import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.items.Gun;
import com.pewpewdungeons.items.RangeWeapon;
import com.pewpewdungeons.items.inventory.PlayerInventory;
import com.pewpewdungeons.world.Dungeon;
import com.pewpewdungeons.Vector2;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import java.util.ArrayList;

import static com.raylib.Raylib.*;

public class Player extends GameObject {
    private Dungeon dungeon;

    private double health;
    private double mana;
    private PlayerInventory inventory;
    private RangeWeapon equippedWeapon = new Gun(new Vector2(0.5f, 0.25f), 1.2f, this);

    public Player(Dungeon dungeon, double health, double mana, Vector2 position, Vector2 size, float speed) {
        this.health = health;
        this.mana = mana;
        this.position = position;
        this.size = size;
        this.speed = speed;
        this.inventory = new PlayerInventory(new ArrayList<RangeWeapon>(), 0);

        this.collider = new RectangleCollider(position, size);
        this.dungeon = dungeon;
    }

    @Override
    public void draw() {
        try (Raylib.Vector2 rPosition = this.position.toNative()) {
            Raylib.DrawRectangleV(rPosition, this.size.toNative(), Jaylib.RED);
        }
        if (equippedWeapon != null)
            if (equippedWeapon instanceof Drawable d)
                d.draw();
    }

    @Override
    public void update(float dt) {
        float dx = 0;
        float dy = 0;
        float v = this.speed * Raylib.GetFrameTime();

        if (Raylib.IsKeyDown(KEY_A))
            dx -= v;
        if (Raylib.IsKeyDown(KEY_D))
            dx += v;

        if (Raylib.IsKeyDown(KEY_W))
            dy -= v;
        if (Raylib.IsKeyDown(KEY_S))
            dy += v;

        RectangleCollider tempCollider = new RectangleCollider(new Vector2(this.position.x + dx, this.position.y + dy),
                this.size);

        if (this.dungeon.contains(tempCollider)) {
            this.position.add(dx, dy);
        }

        if (Raylib.IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
            equippedWeapon.shoot();
        }

        if (equippedWeapon != null)
            if (equippedWeapon instanceof Updatable u)
                u.update(dt);
    }
}
