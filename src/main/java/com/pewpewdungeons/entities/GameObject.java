package com.pewpewdungeons.entities;

import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.Vector2;

public abstract class GameObject implements Drawable, Updatable {

    protected Vector2 position;
    protected Vector2 size;
    protected Vector2 orientation;
    protected float speed;

    public Vector2 getPosition() { return position; }
    public Vector2 getCenterPosition() { return new Vector2(position.x + size.x/2, position.y + size.y/2); }
}
