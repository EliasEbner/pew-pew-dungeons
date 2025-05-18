package com.pewpewdungeons.projectiles;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.entities.Updatable;

public abstract class Projectile implements Updatable, Drawable {

    public Vector2 position;
    public float t; // how long since this projectile's creation?

    protected Projectile(Vector2 position) {
        this.position = position;
        this.t = 0;
    }

    @Override
    public void update(float dt) {
        this.t += dt;
    }

    protected void moveTo(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    protected void moveTo(Vector2 position) {
        moveTo(position.x, position.y);
    }

    protected void moveBy(Vector2 delta) {
        moveBy(delta.x, delta.y);
    }

    protected void moveBy(float dx, float dy) {
        moveTo(position.x + dx, position.y + dy);
    }
}
