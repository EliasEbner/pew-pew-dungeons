package com.pewpewdungeons.entities.enemies;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.Enemy;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.projectiles.BulletProjectile;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.pewpewdungeons.world.Dungeon;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class RangedEnemy extends Enemy {

    private float idealRange = 6.0f;
    private float shootCooldown = 0.0f;
    private float shootCooldownMax = 2.0f; // 2 seconds between shots

    public RangedEnemy(Dungeon dungeon, Vector2 position) {
        super(dungeon, 40, position, new Vector2(1.0f, 1.0f), 1.5f);
        this.detectionRange = 12.0f;
    }

    @Override
    public void update(float dt) {
        // We override the default update to change behavior from melee to ranged
        Player player = dungeon.getPlayer();
        if (player == null) return;

        // Ensure collider position is in sync
        this.collider.position = new Vector2(position);

        // Calculate direction to player
        Vector2 directionToPlayer = new Vector2(player.getPosition());
        directionToPlayer.sub(this.position);
        float distance = directionToPlayer.length();

        // If player is in detection range, move and shoot
        if (distance <= detectionRange) {
            autoMove(); // Use a modified autoMove
            
            // Handle shooting cooldown
            if (shootCooldown > 0) {
                shootCooldown -= dt;
            }

            // Shoot if cooldown is over and player is in sight
            if (shootCooldown <= 0) {
                shootAtPlayer(player);
                shootCooldown = shootCooldownMax;
            }
        }
    }

    @Override
    public void autoMove() {
        Player player = dungeon.getPlayer();
        if (player == null) return;

        Vector2 direction = new Vector2(player.getPosition());
        direction.sub(this.position);
        float distance = direction.length();

        // Move away if too close, move closer if too far, stay put if in ideal range
        if (distance < idealRange - 1.0f) {
            direction.mul(-1); // Move away
        } else if (distance > idealRange + 1.0f) {
            // Move closer, direction is already correct
        } else {
            return; // Don't move
        }
        
        direction.normalize();
        direction.mul(speed * Raylib.GetFrameTime());
        
        // Try to move
        Vector2 newPos = new Vector2(position);
        newPos.add(direction);
        
        if (canMoveTo(newPos)) {
            this.position = newPos;
        }
    }
    
    private void shootAtPlayer(Player player) {
        Vector2 direction = new Vector2(player.getCenterPosition());
        direction.sub(this.getCenterPosition());
        direction.normalize();
        direction.mul(15); // projectile speed

        ProjectileSystem.createProjectile(new BulletProjectile(new Vector2(getCenterPosition()), direction));
    }

    @Override
    public void draw() {
        try (Raylib.Vector2 rPosition = this.position.toNative()) {
            Raylib.DrawRectangleV(rPosition, this.size.toNative(), Jaylib.SKYBLUE);
        }
    }
} 