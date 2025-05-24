package com.pewpewdungeons.world;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.GameObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DungeonGenerator {
  private final static Random random = new Random();

  // Room dimensions
  private final static float minRoomWidth = 10.0f;
  private final static float maxRoomWidth = 20.0f;
  private final static float minRoomHeight = 10.0f;
  private final static float maxRoomHeight = 20.0f;

  // Corridor dimensions
  private final static float corridorWidth = 2.0f;

  // Room spacing
  private final static float roomSpacing = 2.0f;

  // Max room distance
  private final static float maxRoomDistance = 7f;

  // Amount of overlap between the corridors and the room to make sure
  // the player isn't stuck
  private final static float corridorRoomOverlap = 0.0f;

  // Generate a dungeonn layout with the specified number of rooms
  public static Dungeon generateDungeon(int roomCount) {
    List<Room> rooms = new ArrayList<>();

    // Generate rooms in a somewhat linear path
    Vector2 currentPosition = new Vector2(0, 0);
    float roomWidth = minRoomWidth + random.nextFloat() * (maxRoomWidth - minRoomWidth);
    float roomHeight = minRoomHeight + random.nextFloat() * (maxRoomHeight - minRoomHeight);
    Vector2 roomSize = new Vector2(roomWidth, roomHeight);
    Set<GameObject> objectsInRoom = new HashSet<>();

    Room room = new Room(currentPosition, roomSize, objectsInRoom);
    rooms.add(room);

    for (int i = 0; i < roomCount; i++) {
      // Generate room size
      roomWidth = minRoomWidth + random.nextFloat() * (maxRoomWidth - minRoomWidth);
      roomHeight = minRoomHeight + random.nextFloat() * (maxRoomHeight - minRoomHeight);
      roomSize = new Vector2(roomWidth, roomHeight);

      // Empty set for objects in the room (will be populated later)
      objectsInRoom = new HashSet<>();

      currentPosition = calculateNextRoomPosition(currentPosition, roomSize, rooms.getLast());

      // Create the room
      room = new Room(currentPosition, roomSize, objectsInRoom);
      rooms.add(room);
    }

    for (int i = 0; i < roomCount - 1; i++) {
      Room room0 = rooms.get(i);
      Room room1 = rooms.get(i + 1);

      float ax0 = (room0.getPosition().x);
      float ax1 = (room0.getPosition().x + room0.getSize().x);
      float ay0 = (room0.getPosition().y);
      float ay1 = (room0.getPosition().y + room0.getSize().y);

      float bx0 = (room1.getPosition().x);
      float bx1 = (room1.getPosition().x + room1.getSize().x);
      float by0 = (room1.getPosition().y);
      float by1 = (room1.getPosition().y + room1.getSize().y);

      Vector2 corridorPosition = new Vector2();
      Vector2 corridorSize = new Vector2();

      // if the rooms overlap in the y axis
      if (by1 - ay0 >= corridorWidth && ay1 - by0 >= corridorWidth) {
        corridorPosition.y = random.nextFloat(
            Math.max(
                ay0, by0),
            Math.min(
                ay1 - corridorWidth,
                by1 - corridorWidth));

        corridorSize.y = corridorWidth;

        // if the second room is to the right of the first
        if (ax1 < bx0) {
          corridorSize.x = bx0 - ax1;
          corridorPosition.x = ax1;
          // if the second room is to the left of the first
        } else if (bx1 < ax0) {
          corridorSize.x = ax0 - bx1;
          corridorPosition.x = bx1;
        } else {
          System.out.println("Overlapping rooms. Cannot connect with corridors.");
        }
        // if they overlap in the x axis
      } else if (bx1 - ax0 >= corridorWidth && ax1 - bx0 >= corridorWidth) {
        corridorPosition.x = random.nextFloat(
            Math.max(
                ax0, bx0),
            Math.min(
                ax1 - corridorWidth,
                bx1 - corridorWidth));

        corridorSize.x = corridorWidth;

        // if the second room is below the first
        if (ay1 < by0) {
          corridorSize.y = by0 - ay1;
          corridorPosition.y = ay1;
          // if the second room is above the first
        } else if (by1 < ay0) {
          corridorSize.y = ay0 - by1;
          corridorPosition.y = by1;
        } else {
          System.out.println("Overlapping rooms. Cannot connect with corridors.");
        }
      } else {
        System.out.println("Rooms cannot be connected with a straight horizontal or vertical corrior.");
      }

      rooms.add(new Room(corridorPosition, corridorSize, new HashSet<GameObject>()));
    }

    // Create the dungeon with generated rooms
    return new Dungeon(rooms);
  }

  private static Vector2 calculateNextRoomPosition(Vector2 currentPos, Vector2 roomSize, Room lastRoom) {
    Vector2 nextPosition = new Vector2();
    int direction = random.nextInt(3);

    // randomly place the room but still make sure that it follows
    // somewhat of a logical path and that the rooms don't overlap
    switch (direction) {
      case 0:
        nextPosition.x = currentPos.x + random.nextFloat(lastRoom.getSize().x - corridorWidth * 2) + corridorWidth;
        nextPosition.y = currentPos.y + lastRoom.getSize().y + roomSpacing + random.nextFloat(maxRoomDistance);
        break;

      case 1:
        nextPosition.x = currentPos.x + lastRoom.getSize().x + roomSpacing + random.nextFloat(maxRoomDistance);
        nextPosition.y = currentPos.y + random.nextFloat(lastRoom.getSize().y - corridorWidth * 2) + corridorWidth;
        break;

      case 2:
        nextPosition.x = currentPos.x - roomSize.x - roomSpacing - random.nextFloat(maxRoomDistance);
        nextPosition.y = currentPos.y - random.nextFloat(lastRoom.getSize().y - corridorWidth * 2) - corridorWidth;
        break;

      case 3:
        nextPosition.x = currentPos.x - random.nextFloat(lastRoom.getSize().x - corridorWidth * 2) - corridorWidth;
        nextPosition.y = currentPos.y - roomSize.y - roomSpacing - random.nextFloat(maxRoomDistance);
        break;

      case 4:
        nextPosition.x = currentPos.x + lastRoom.getSize().x + roomSpacing + random.nextFloat(maxRoomDistance);
        nextPosition.y = currentPos.y + lastRoom.getSize().y + roomSpacing + random.nextFloat(maxRoomDistance);
        break;

      case 5:
        nextPosition.x = currentPos.x - roomSize.x - roomSpacing - random.nextFloat(maxRoomDistance);
        nextPosition.y = currentPos.y + lastRoom.getSize().y + roomSpacing + random.nextFloat(maxRoomDistance);
        break;

      case 6:
        nextPosition.x = currentPos.x - roomSize.x - roomSpacing - random.nextFloat(maxRoomDistance);
        nextPosition.y = currentPos.y - roomSize.y - roomSpacing - random.nextFloat(maxRoomDistance);
        break;

      case 7:
        nextPosition.x = currentPos.x + lastRoom.getSize().x + roomSpacing + random.nextFloat(maxRoomDistance);
        nextPosition.y = currentPos.y - roomSize.y - roomSpacing - random.nextFloat(maxRoomDistance);
        break;
    }

    return nextPosition;
  }
}
