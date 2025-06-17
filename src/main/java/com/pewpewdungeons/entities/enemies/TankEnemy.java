package com.pewpewdungeons.entities.enemies;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.Enemy;
import com.pewpewdungeons.world.Dungeon;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class TankEnemy extends Enemy {

    public TankEnemy(Dungeon dungeon, Vector2 position) {
        // High health, low speed, high damage
        super(dungeon, 250, position, new Vector2(1.5f, 1.5f), 1.0f);
        this.attackDamage = 25.0f;
        this.attackRange = 1.8f;
        this.detectionRange = 7.0f;
    }

    @Override
    public void draw() {
        try (Raylib.Vector2 rPosition = this.position.toNative()) {
            Raylib.DrawRectangleV(rPosition, this.size.toNative(), Jaylib.MAROON);
        }
    }
} 