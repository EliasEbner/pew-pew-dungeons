package com.pewpewdungeons.collider;

import com.pewpewdungeons.Vector2;
import com.raylib.Raylib;

public class NullCollider extends Collider {
    public NullCollider(Vector2 position, Vector2 size) {
        super(position, size);
    }

    @Override
    public boolean collide(CircleCollider b) {
        return false;
    }

    @Override
    public boolean collide(RectangleCollider b) {
        return false;
    }

    @Override
    public void debugDraw(Raylib.Color color) {
    }
}
