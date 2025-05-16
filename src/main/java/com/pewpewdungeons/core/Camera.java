package com.pewpewdungeons.core;

import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.physics.Vector2;

public class Camera {

    private Vector2 position;

    public Camera(Vector2 position) {
        this.position = position;
    }

    public void update(Player player) {
        // logic to follow player
    }

    public Vector2 getPosition() {
        return this.position;
    }
}
