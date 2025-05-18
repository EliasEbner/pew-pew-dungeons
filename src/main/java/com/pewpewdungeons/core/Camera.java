package com.pewpewdungeons.core;

import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.Vector2;

public class Camera {

    private Vector2 position;

    public Camera(Vector2 position) {
    }

    public void update(Player player) {
        // logic to follow player
    }

    public Vector2 getPosition() {
        return this.position;
    }
}
