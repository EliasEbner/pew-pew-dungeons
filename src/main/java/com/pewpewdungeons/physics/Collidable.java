package com.pewpewdungeons.physics;

import com.pewpewdungeons.physics.rigidBody.RigidBody;

public interface Collidable {
    void collidesWith(RigidBody body);
}
