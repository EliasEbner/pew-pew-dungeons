package com.pewpewdungeons.physics.rigidBody;

import com.pewpewdungeons.Vector2;

public class RecRigidBody extends RigidBody {

    public RecRigidBody(Vector2 position, Vector2 size) {
        super(position, size);
    }

    @Override
    public void collidesWith(RigidBody body) {
        return;
    }
}
