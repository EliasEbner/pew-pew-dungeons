package com.pewpewdungeons.collider;

import com.pewpewdungeons.Vector2;
import com.raylib.Raylib;

public class RectangleCollider extends Collider {
    public RectangleCollider(Vector2 position, Vector2 size) {
        super(position, size);
    }

    @Override
    public boolean collide(CircleCollider b) {
        Vector2 circleCenter = b.getCenter();
        Vector2 rectCenter = this.getCenter();
        float radius = b.radius;

        float dx = Math.abs(rectCenter.x - circleCenter.x);
        float dy = Math.abs(rectCenter.y - circleCenter.y);

        if (dx > (size.x / 2 + radius))
            return false;
        if (dy > (size.y / 2 + radius))
            return false;

        if (dx <= size.x / 2)
            return true;
        if (dy <= size.y / 2)
            return true;

        // Distance to corner.
        dx -= size.x / 2;
        dy -= size.y / 2;

        return (dx * dx + dy * dy) <= radius * radius;
    }

    @Override
    public boolean collide(RectangleCollider b) {
        float ax0 = position.x;
        float ay0 = position.y;
        float ax1 = position.x + size.x;
        float ay1 = position.y + size.y;

        float bx0 = b.position.x;
        float by0 = b.position.y;
        float bx1 = b.position.x + b.size.x;
        float by1 = b.position.y + b.size.y;

        // Quick cull.
        if (ax0 > bx1 || bx0 > ax1)
            return false;
        if (ay0 > by1 || by0 > ay1)
            return false;

        return true;
    }

    @Override
    public boolean contains(Vector2 point) {
        float ax0 = position.x;
        float ay0 = position.y;
        float ax1 = position.x + size.x;
        float ay1 = position.y + size.y;

        if (point.x > ax0 && point.x < ax1 && point.y > ay0 && point.y < ay1) {
            return true;
        }

        return false;
    }

    @Override
    public void debugDraw(Raylib.Color color) {
        Raylib.Vector2 rPosition = position.toNative();
        Raylib.Vector2 rSize = size.toNative();
        Raylib.DrawRectangleV(rPosition, rSize, color);
        rSize.close();
        rPosition.close();
    }

    @Override
    public boolean insideOf(RectangleCollider b) {
        float ax0 = this.position.x;
        float ay0 = this.position.y;
        float ax1 = this.position.x + this.size.x;
        float ay1 = this.position.y + this.size.y;

        float bx0 = b.position.x;
        float by0 = b.position.y;
        float bx1 = b.position.x + b.size.x;
        float by1 = b.position.y + b.size.y;

        if (ax0 > bx0 && ax1 < bx1 && ay0 > by0 && ay1 < by1) {
            return true;
        }

        return false;
    }
}
