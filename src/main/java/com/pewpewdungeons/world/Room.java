package com.pewpewdungeons.world;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.collider.Collider;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Updatable;
import com.pewpewdungeons.enums.DoorPositionEnum;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public final class Room implements Drawable, Updatable {
    final float doorWidth = 30;
    final float doorLength = 50;

    private Vector2 position;
    private Vector2 size;

    private Set<GameObject> objectsInRoom;
    private Set<DoorPositionEnum> doorPositions;

    private Set<RectangleCollider> colliders = new HashSet<RectangleCollider>();

    public Room(Vector2 position, Vector2 size, Set<GameObject> objectsInRoom, Set<DoorPositionEnum> doorPositions) {
        this.position = position;
        this.size = size;
        this.objectsInRoom = objectsInRoom;
        this.doorPositions = doorPositions;
        this.colliders.add(new RectangleCollider(position, size));
        if (this.doorPositions.contains(DoorPositionEnum.TOP)) {
            Vector2 tempPosition = new Vector2(
                    this.position.x + this.size.x / 2 - this.doorWidth / 2,
                    this.position.y - this.doorLength);

            this.colliders.add(
                    new RectangleCollider(
                            tempPosition,
                            new Vector2(this.doorWidth, this.doorLength)));
        }
        if (this.doorPositions.contains(DoorPositionEnum.BOTTOM)) {
            Vector2 tempPosition = new Vector2(
                    this.position.x + this.size.x / 2 - this.doorWidth / 2,
                    this.position.y + this.size.y);

            this.colliders.add(
                    new RectangleCollider(
                            tempPosition,
                            new Vector2(this.doorWidth, this.doorLength)));
        }
        if (this.doorPositions.contains(DoorPositionEnum.LEFT)) {
            Vector2 tempPosition = new Vector2(
                    this.position.x - this.doorLength,
                    this.position.y + this.size.y / 2 - this.doorWidth);

            this.colliders.add(
                    new RectangleCollider(
                            tempPosition,
                            new Vector2(this.doorLength, this.doorWidth)));
        }
        if (this.doorPositions.contains(DoorPositionEnum.RIGHT)) {
            Vector2 tempPosition = new Vector2(
                    this.position.x + this.size.x,
                    this.position.y + this.size.y / 2 - this.doorWidth);

            this.colliders.add(
                    new RectangleCollider(
                            tempPosition,
                            new Vector2(this.doorLength, this.doorWidth)));
        }
    }

    public void draw() {
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

    public Set<RectangleCollider> getColliders() {
        return this.colliders;
    }

    public Set<GameObject> getObjects() {
        return Set.copyOf(this.objectsInRoom);
    }

    public Set<DoorPositionEnum> getDoorPositions() {
        return Set.copyOf(this.doorPositions);
    }
}
