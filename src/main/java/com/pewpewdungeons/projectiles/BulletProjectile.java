package com.pewpewdungeons.projectiles;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.collider.CircleCollider;
import com.pewpewdungeons.entities.Enemy;
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
        
        // Move projectile
        moveBy(velocity.x * dt, velocity.y * dt);
        collider.position = new Vector2(position);
        
        // Check for collisions with enemies
        for (Enemy enemy : ProjectileSystem.getDungeon().getEnemies()) {
            if (collider.intersects(enemy.getCollider())) {
                enemy.takeDamage(damage);
                hasHit = true;
                ProjectileSystem.removeProjectile(this);
                break;
            }
        }

        // Remove bullet after a certain time
        if (this.t > 1) ProjectileSystem.removeProjectile(this);
    }
    
    public CircleCollider getCollider() {
        return collider;
    }
    
    public float getDamage() {
        return damage;
    }
}
