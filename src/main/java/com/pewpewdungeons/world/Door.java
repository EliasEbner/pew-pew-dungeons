package com.pewpewdungeons.world;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.enums.DoorOrientationEnum;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class Door extends GameObject {
  private final float DOOR_THICKNESS = 0.5f;
  private final float TRIGGER_DISTANCE = 5f;
  private final float OPENING_SPEED = 10f;

  private final Player player;
  private final DoorOrientationEnum orientation;
  private final float maxSize;

  public Door(Player player, Vector2 roomPosition, Vector2 roomSize, DoorOrientationEnum orientation) {
    this.orientation = orientation;
    this.player = player;

    this.size = new Vector2();
    this.position = new Vector2();

    switch (orientation) {
      case HORIZONTAL:
        this.size.x = roomSize.x;
        this.size.y = this.DOOR_THICKNESS;

        this.position.x = roomPosition.x;
        this.position.y = roomPosition.y + roomSize.y / 2 - this.DOOR_THICKNESS / 2;

        this.maxSize = roomSize.x;

        break;

      case VERTICAL:
        this.size.x = this.DOOR_THICKNESS;
        this.size.y = roomSize.y;

        this.position.x = roomPosition.x + roomSize.x / 2 - this.DOOR_THICKNESS / 2;
        this.position.y = roomPosition.y;

        this.maxSize = roomSize.y;

        break;

      default:
        this.maxSize = 0;
    }

    this.collider = new RectangleCollider(this.position, this.size);

  }

  public void draw() {
    Raylib.Vector2 raylibPositionVector = this.position.toNative();
    Raylib.Vector2 raylibSizeVector = this.size.toNative();

    Raylib.DrawRectangleV(raylibPositionVector, raylibSizeVector, Jaylib.DARKGRAY);

    raylibPositionVector.close();
    raylibSizeVector.close();
  }

  public void update(float deltaTime) {
    boolean isOpening = this.player.getCollider().getCenter()
        .distanceSq(this.collider.getCenter()) < this.TRIGGER_DISTANCE;

    if (isOpening) {
      switch (this.orientation) {
        case HORIZONTAL:
          this.size.x = Math.max(0, this.size.x - this.OPENING_SPEED * deltaTime);
          break;
        case VERTICAL:
          this.size.y = Math.max(0, this.size.y - this.OPENING_SPEED * deltaTime);
          break;
      }
    } else {
      switch (this.orientation) {
        case HORIZONTAL:
          this.size.x = Math.min(this.maxSize, this.size.x + this.OPENING_SPEED * deltaTime);
          break;
        case VERTICAL:
          this.size.y = Math.min(this.maxSize, this.size.y + this.OPENING_SPEED * deltaTime);
          break;
      }
    }

    this.collider.size = this.size;
  }
}
