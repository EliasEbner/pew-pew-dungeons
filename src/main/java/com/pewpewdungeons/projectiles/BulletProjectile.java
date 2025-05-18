package com.pewpewdungeons.projectiles;

import com.pewpewdungeons.Vector2;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class BulletProjectile extends Projectile {

    public Vector2 velocity;

    public BulletProjectile(Vector2 position, Vector2 velocity) {
        super(position);
        this.velocity = velocity;
    }

    @Override
    public void draw() {
        try (Raylib.Vector2 rPosition = position.toNative()) {
            Raylib.DrawCircleV(rPosition, 0.25f, Jaylib.RED);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        moveBy(velocity.x * dt, velocity.y * dt);

        if (this.t > 1) ProjectileSystem.removeProjectile(this);
    }
}
