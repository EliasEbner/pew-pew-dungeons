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
    private Random random = new Random();
    
    // Room dimensions
    private float minRoomWidth = 8.0f;
    private float maxRoomWidth = 15.0f;
    private float minRoomHeight = 8.0f;
    private float maxRoomHeight = 15.0f;
    
    // Corridor dimensions
    private float corridorWidth = 3.0f;
    
    // Room spacing
    private float roomSpacing = 5.0f;

    //Generate a dungeonn layout with the specified number of rooms
    public Dungeon generateDungeon(int roomCount) {
        List<Room> rooms = new ArrayList<>();
        
        // Generate rooms in a somewhat linear path
        Vector2 currentPosition = new Vector2(0, 0);
        
        for (int i = 0; i < roomCount; i++) {
            //Generate room size
            float roomWidth = minRoomWidth + random.nextFloat() * (maxRoomWidth - minRoomWidth);
            float roomHeight = minRoomHeight + random.nextFloat() * (maxRoomHeight - minRoomHeight);
            Vector2 roomSize = new Vector2(roomWidth, roomHeight);
            
            // Empty set for objects in the room (willbe populated later)
            Set<GameObject> objectsInRoom = new HashSet<>();
            
            // Determine door positions
            Set<DoorPositionEnum> doorPositions = new HashSet<>();
            
            // Always connect to previous room, excewpt for first room
            if (i > 0) {
                // Determine which side to connect from based on direction
                DoorPositionEnum entranceDoor = getRandomDoorPosition();
                doorPositions.add(entranceDoor);
                
                // Position room based on door position
                positionRoomRelativeToPath(currentPosition, roomSize, entranceDoor);
            }
            
            // Add exit door if not the last room
            if (i < roomCount - 1) {
                // Random exit direction
                DoorPositionEnum exitDoor;
                do {
                    exitDoor = getRandomDoorPosition();
                } while (doorPositions.contains(exitDoor));
                
                doorPositions.add(exitDoor);
                
                // Update current position for next room
                currentPosition = calculateNextRoomPosition(currentPosition, roomSize, exitDoor);
            }
            
            // Create the room
            Room room = new Room(currentPosition, roomSize, objectsInRoom, doorPositions);
            rooms.add(room);
        }
        
        // Create the dungeon with generated rooms
        return new Dungeon(rooms);
    }

    private DoorPositionEnum getRandomDoorPosition() {
        DoorPositionEnum[] positions = DoorPositionEnum.values();
        return positions[random.nextInt(positions.length)];
    }

    private void positionRoomRelativeToPath(Vector2 position, Vector2 size, DoorPositionEnum doorPosition) {
        switch (doorPosition) {
            case TOP:
                position.y -= size.y + roomSpacing;
                position.x -= size.x / 2; // Center horizontally
                break;
            case BOTTOM:
                // Room stays at current position
                position.x -= size.x / 2; // Center horizontally
                break;
            case LEFT:
                position.x -= size.x + roomSpacing;
                position.y -= size.y / 2; // Center vertically
                break;
            case RIGHT:
                // Room stays at current position
                position.y -= size.y / 2; // Center vertically
                break;
        }
    }

    private Vector2 calculateNextRoomPosition(Vector2 currentPos, Vector2 currentSize, DoorPositionEnum exitDoor) {
        Vector2 nextPosition = new Vector2(currentPos);
        
        switch (exitDoor) {
            case TOP:
                nextPosition.y = currentPos.y - roomSpacing;
                nextPosition.x = currentPos.x + (currentSize.x / 2); // Center of top edge
                break;
            case BOTTOM:
                nextPosition.y = currentPos.y + currentSize.y + roomSpacing;
                nextPosition.x = currentPos.x + (currentSize.x / 2); // Center of bottom edge
                break;
            case LEFT:
                nextPosition.x = currentPos.x - roomSpacing;
                nextPosition.y = currentPos.y + (currentSize.y / 2); // Center of left edge
                break;
            case RIGHT:
                nextPosition.x = currentPos.x + currentSize.x + roomSpacing;
                nextPosition.y = currentPos.y + (currentSize.y / 2); // Center of right edge
                break;
        }
        
        return nextPosition;
    }
} 