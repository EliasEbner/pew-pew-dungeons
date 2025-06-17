package com.pewpewdungeons.entities.enemies;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.Enemy;
import com.pewpewdungeons.world.Dungeon;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class RunnerEnemy extends Enemy {

    public RunnerEnemy(Dungeon dungeon, Vector2 position) {
        // Low health, high speed, low damage
        super(dungeon, 30, position, new Vector2(0.8f, 0.8f), 4.0f);
        this.attackDamage = 5.0f;
        this.attackRange = 1.0f;
        this.detectionRange = 9.0f;
    }

    @Override
    public void draw() {
        try (Raylib.Vector2 rPosition = this.position.toNative()) {
            Raylib.DrawRectangleV(rPosition, this.size.toNative(), Jaylib.LIME);
        }
    }
} 