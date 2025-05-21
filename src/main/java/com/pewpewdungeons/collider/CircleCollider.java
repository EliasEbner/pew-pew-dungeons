package com.pewpewdungeons.collider;

import com.pewpewdungeons.Vector2;
import com.raylib.Raylib;

public class CircleCollider extends Collider {
    public float radius;

    public CircleCollider(Vector2 topLeftPos, float radius) {
        super(topLeftPos, new Vector2(2 * radius, 2 * radius));
        this.radius = radius;
    }

    @Override
    public boolean collide(CircleCollider b) {
        Vector2 centerA = getCenter();
        Vector2 centerB = b.getCenter();
        float d2 = Vector2.distanceSquared(centerA, centerB);
        float r2 = (this.radius + b.radius);
        r2 *= r2;
        return (d2 < r2);
    }

    @Override
    public boolean collide(RectangleCollider b) {
        return b.collide(this);
    }

    @Override
    public boolean insideOf(RectangleCollider b) {
        float ax0 = this.position.x - this.radius;
        float ay0 = this.position.y - this.radius;
        float ax1 = this.position.x + this.radius;
        float ay1 = this.position.y + this.radius;

        float bx0 = b.position.x;
        float by0 = b.position.y;
        float bx1 = b.position.x + b.size.x;
        float by1 = b.position.y + b.size.y;

        if (ax0 > bx0 && ax1 < bx1 && ay0 > by0 && ay1 < by1) {
            return true;
        }

        return false;
    }

    @Override
    public boolean contains(Vector2 point) {
        Vector2 fromCenter = this.getCenter();
        fromCenter.sub(point);

        if (fromCenter.length() < this.radius) {
            return true;
        }

        return false;
    }

    @Override
    public void debugDraw(Raylib.Color color) {
        try (Raylib.Vector2 rCenter = getCenter().toNative()) {
            Raylib.DrawCircleV(rCenter, radius, color);
        }
    }
}
