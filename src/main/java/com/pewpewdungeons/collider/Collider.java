package com.pewpewdungeons.collider;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.GameObject;
import com.raylib.Raylib;

public abstract class Collider {

    public Vector2 position;
    public Vector2 size;

    public Collider(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    public Vector2 getCenter() {
        return new Vector2(position.x + size.x/2, position.y + size.y/2);
    }

    public abstract boolean collide(CircleCollider b);
    public abstract boolean collide(RectangleCollider b);

    public abstract void debugDraw(Raylib.Color color);

    public boolean collide(GameObject g) {
        return collide(g.getCollider());
    }

    public boolean collide(Collider b) {
        switch (b) {
            case CircleCollider    c -> { return collide(c); }
            case RectangleCollider r -> { return collide(r); }
            case NullCollider      n -> { return collide(n); }
            case null, default -> {
                assert false;
            }
        }
        return false;
    }
}
