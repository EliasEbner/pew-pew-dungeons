package com.pewpewdungeons.world;

import java.util.HashSet;
import java.util.Set;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Updatable;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public final class Room extends GameObject {
  final float doorWidth = 30;
  final float doorLength = 50;

  private Set<GameObject> objectsInRoom;

  private Set<RectangleCollider> colliders = new HashSet<RectangleCollider>();

  public Room(Vector2 position, Vector2 size, Set<GameObject> objectsInRoom) {
    this.position = position;
    this.size = size;
    this.objectsInRoom = objectsInRoom;
    this.colliders.add(new RectangleCollider(position, size));
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

  public Vector2 getPosition() {
    return new Vector2(position);
  }

  public Vector2 getSize() {
    return new Vector2(size);
  }

  public void addObject(GameObject object) {
    objectsInRoom.add(object);
  }

  public void removeObject(GameObject object) {
    objectsInRoom.remove(object);
  }
}
