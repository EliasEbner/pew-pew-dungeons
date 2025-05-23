package com.pewpewdungeons.world;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.enums.DoorPositionEnum;

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
  private final static float corridorWidth = 3.0f;

  // Room spacing
  private final static float roomSpacing = 2.0f;

  // Max room distance
  private final static float maxRoomDistance = 7f;

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

      int ax0 = (int) (room0.getPosition().x);
      int ax1 = (int) (room0.getPosition().x + room0.getSize().x);
      int ay0 = (int) (room0.getPosition().y);
      int ay1 = (int) (room0.getPosition().y + room0.getSize().y);

      int bx0 = (int) (room1.getPosition().x);
      int bx1 = (int) (room1.getPosition().x + room1.getSize().x);
      int by0 = (int) (room1.getPosition().y);
      int by1 = (int) (room1.getPosition().y + room1.getSize().y);

      // TODO connect rooms
    }

    // Create the dungeon with generated rooms
    return new Dungeon(rooms);
  }

  private static Vector2 calculateNextRoomPosition(Vector2 currentPos, Vector2 roomSize, Room lastRoom) {
    Vector2 nextPosition = new Vector2();
    int direction = random.nextInt(8);

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
