package com.pewpewdungeons.entities;

import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.core.Updatable;
import com.pewpewdungeons.physics.Vector2;

public abstract class GameObject implements Drawable, Updatable {
    protected Vector2 position;
    protected Vector2 size;
    protected Vector2 orientation;
}