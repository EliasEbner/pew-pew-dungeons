package com.pewpewdungeons.world;

import java.util.List;
import java.util.Set;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Updatable;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public final class Room implements Drawable, Updatable {
    private Vector2 position;
    private Vector2 size;

    private List<GameObject> objectsInRoom;
    private Set<DoorPositionEnum> doorPositions;

    public Room(Vector2 position, Vector2 size, List<GameObject> objectsInRoom, Set<DoorPositionEnum> doorPositions) {
        this.position = position;
        this.size = size;
        this.objectsInRoom = objectsInRoom;
        this.doorPositions = doorPositions;
    }

    public void draw() {
        /*
         * System.out.println("position.x: " + this.position.x);
         * System.out.println("position.y: " + this.position.y);
         * System.out.println("size.x: " + this.size.x);
         * System.out.println("size.y: " + this.size.y);
         */
        Raylib.Vector2 raylibPositionVector = this.position.toNative();
        Raylib.Vector2 raylibSizeVector = this.size.toNative();

        Raylib.DrawRectangleV(raylibPositionVector, raylibSizeVector, Jaylib.GRAY);

        raylibPositionVector.close();
        raylibSizeVector.close();

        this.objectsInRoom.forEach(GameObject::draw);
    }

    public void update(float deltaTime) {
        this.objectsInRoom.forEach(object -> object.update(deltaTime));
    }

    public List<GameObject> getObjects() {
        return List.copyOf(this.objectsInRoom);
    }

    public Set<DoorPositionEnum> getDoorPositions() {
        return Set.copyOf(this.doorPositions);
    }
}
