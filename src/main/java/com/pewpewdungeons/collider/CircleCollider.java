package com.pewpewdungeons.collider;

import com.pewpewdungeons.Vector2;
import com.raylib.Raylib;

public class CircleCollider extends Collider {
    public float radius;

    public CircleCollider(Vector2 topLeftPos, float radius) {
        super(topLeftPos, new Vector2(2*radius, 2*radius));
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
    public void debugDraw(Raylib.Color color) {
        try (Raylib.Vector2 rCenter = getCenter().toNative()) {
            Raylib.DrawCircleV(rCenter, radius, color);
        }
    }
}
