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

public class Sword extends GameObject implements Weapon {

    protected float distanceFromPlayer;
    protected Player player;
    protected Raylib.Color color = Jaylib.BLUE;

    protected double lastShotTime = GetTime();
    protected double shootingDelay = 0.5;

    public Sword(Vector2 size, float distanceFromPlayer, Player player) {
        this.size = new Vector2(2f, 2f); // store sword hitbox size
        this.distanceFromPlayer = 0f;
        this.player = player;
    }

    protected boolean canShoot() {
        return GetTime() > lastShotTime + shootingDelay;
    }

    @Override
    public void shoot() {
        if (!canShoot()) return;

        // Example: swing at fixed radius from player center
        MeeleProjectile slash = new MeeleProjectile(
            player,
            new Vector2(2, 1),  // size of the sword hitbox (width, height)
            0                    // distance from player center (radius)
        );

        ProjectileSystem.createProjectile(slash);
        lastShotTime = GetTime();
    }

    @Override
    public void update(float dt) {
        // Update sword orientation to face the mouse - store it somewhere if needed
        Vector2 dir = Main.getMouseWorldPosition();
        dir.sub(player.getCenterPosition());
        dir.normalize();
        this.orientation = dir;  // assuming Sword has an orientation field you want to keep
    }

    @Override
    public void draw() {
        // TODO optional draw the sword
    }
}
