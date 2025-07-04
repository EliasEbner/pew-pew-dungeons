package com.pewpewdungeons.projectiles;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.logging.GameLogger;
import com.pewpewdungeons.collider.CircleCollider;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.entities.Enemy;
import com.pewpewdungeons.world.Room;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class BulletProjectile extends Projectile {

    public Vector2 velocity;
    private float damage = 25.0f;
    private CircleCollider collider;
    private float radius = 0.25f;
    private boolean hasHit = false;

    public BulletProjectile(Vector2 position, Vector2 velocity) {
        super(position);
        this.velocity = velocity;
        this.collider = new CircleCollider(position, radius);
    }

    @Override
    public void draw() {
        try (Raylib.Vector2 rPosition = position.toNative()) {
            Raylib.DrawCircleV(rPosition, radius, Jaylib.RED);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        
        // Don't update if already hit something
        if (hasHit) return;
        
        // Calculate new position
        Vector2 newPosition = new Vector2(position);
        newPosition.add(new Vector2(velocity.x * dt, velocity.y * dt));
        
        // Create temporary collider at new position to check for wall collision
        CircleCollider tempCollider = new CircleCollider(newPosition, radius);
        
        // Check if the bullet would be inside a room after moving
        boolean insideRoom = false;
        for (Room room : ProjectileSystem.getDungeon().getRooms()) {
            for (RectangleCollider roomCollider : room.getColliders()) {
                if (tempCollider.insideOf(roomCollider)) {
                    insideRoom = true;
                    break;
                }
            }
            if (insideRoom) break;
        }
        
        if (insideRoom) {
            // If inside a room, move the bullet
            moveBy(velocity.x * dt, velocity.y * dt);
            collider.position = new Vector2(position);
            
            // Check for collisions with enemies
            for (Enemy enemy : ProjectileSystem.getDungeon().getEnemies()) {
                if (collider.intersects(enemy.getCollider())) {
                    enemy.takeDamage(damage);
                    hasHit = true;
                    GameLogger.logProjectileEvent("BulletProjectile", "Hit enemy for " + damage + " damage");
                    ProjectileSystem.removeProjectile(this);
                    break;
                }
            }
        } else {
            // Bullet hit a wall
            hasHit = true;
            GameLogger.Projectiles.debug("BulletProjectile hit wall");
            ProjectileSystem.removeProjectile(this);
        }

        // Remove bullet after a certain time
        if (this.t > 1) {
            GameLogger.Projectiles.debug("BulletProjectile expired after 1 second");
            ProjectileSystem.removeProjectile(this);
        }
    }
    
    public CircleCollider getCollider() {
        return collider;
    }
    
    public float getDamage() {
        return damage;
    }
}
