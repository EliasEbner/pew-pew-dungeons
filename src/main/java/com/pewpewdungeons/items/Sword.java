package com.pewpewdungeons.items;

import static com.raylib.Raylib.GetTime;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.projectiles.*;
import com.pewpewdungeons.world.Room;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class Sword extends Weapon {

    protected Player player;
    protected double lastSwingTime = 0;
    protected double swingCooldown = 0.5; // Cooldown in seconds
    protected float swingRange = 1.5f; // The distance from player the sword hitbox appears
    protected Vector2 swingSize = new Vector2(2.0f, 2.0f); // width and height of hitbox

    public Sword(Vector2 size, float distanceFromPlayer, Player player) {
        this.size = size;
        this.player = player;
    }

    protected boolean canShoot() {
        return GetTime() > lastSwingTime + swingCooldown;
    }

    @Override
    public void shoot() {
        if (!canShoot()) return;

        MeeleProjectile slash = new MeeleProjectile(
            player,
            swingSize,
            swingRange
        );

        ProjectileSystem.createProjectile(slash);
        lastSwingTime = GetTime();
    }

    @Override
    public void update(float dt) {
        // The sword itself doesn't need much update logic,
        // the MeeleProjectile handles the swing animation and collision.
    }

    @Override
    public void draw() {
        // The MeeleProjectile handles drawing the swing.
        // We could draw the sword on the player's back or side here
        // if we had sprites. For now, nothing is needed.
    }
}
