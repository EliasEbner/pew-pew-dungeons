package com.pewpewdungeons;

import static com.raylib.Jaylib.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pewpewdungeons.collider.CircleCollider;
import com.pewpewdungeons.collider.Collider;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.pewpewdungeons.world.DoorPositionEnum;
import com.pewpewdungeons.world.Dungeon;
import com.pewpewdungeons.world.Room;
import com.raylib.Raylib;

public final class Main {

    static Vector2 mousePosition;
    static Vector2 mouseWorldPosition;

    public static Vector2 getMousePosition() {
        return mousePosition;
    }

    public static Vector2 getMouseWorldPosition() {
        return new Vector2(mouseWorldPosition);
    }

    public static void main(String[] args) {
        int screenWidth = 1280;
        int screenHeight = 720;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals("-screenWidth")) {
                assert i + 1 < args.length;
                screenWidth = Integer.parseInt(args[++i]);
            } else if (arg.equals("-screenHeight")) {
                assert i + 1 < args.length;
                screenHeight = Integer.parseInt(args[++i]);
            } else {
                System.out.println("Unknown argument " + arg);
            }
        }

        InitWindow(screenWidth, screenHeight, "Pew-Pew-Dungeons");
        SetTargetFPS(60);

        float screenAspect = (float) screenWidth / (float) screenHeight;
        float viewWidthInWorldSpaceUnits = 40;
        float viewHeightInWorldSpaceUnits = viewWidthInWorldSpaceUnits / screenAspect;

        List<Room> rooms = new ArrayList<Room>();
        List<GameObject> objectsInRoom = new ArrayList<GameObject>();
        Vector2 roomPosition = new Vector2(10, 10);
        Vector2 roomSize = new Vector2((float) 10, (float) 10.0);
        Set<DoorPositionEnum> doorPositions = new HashSet<DoorPositionEnum>();
        rooms.add(new Room(roomPosition, roomSize, objectsInRoom, doorPositions));

        Dungeon dungeon = new Dungeon(rooms);

        Collider collider1 = new RectangleCollider(new Vector2(4, 4), new Vector2(4, 2));
        Collider collider2 = new CircleCollider(new Vector2(10, 8), 2);

        Vector2 cameraOffset = new Vector2((float) screenWidth / 2, (float) screenHeight / 2);
        Vector2 cameraTarget; // in world-space.
        float cameraZoom;

        Camera2D nativeCamera = new Camera2D();

        while (!WindowShouldClose()) {

            cameraTarget = new Vector2(viewWidthInWorldSpaceUnits / 2, viewHeightInWorldSpaceUnits / 2);
            // cameraTarget = player.getPosition();
            cameraZoom = (float) screenWidth / viewWidthInWorldSpaceUnits;

            // Setup native camera.
            try (Raylib.Vector2 nativeCameraOffset = cameraOffset.toNative()) {
                nativeCamera.offset(nativeCameraOffset);
            }
            try (Raylib.Vector2 nativeCameraTarget = cameraTarget.toNative()) {
                nativeCamera.target(nativeCameraTarget);
            }
            nativeCamera.zoom(cameraZoom);

            // Get screen and world-space position of mouse.
            {
                Raylib.Vector2 pos = Raylib.GetMousePosition();
                Raylib.Vector2 posWS = GetScreenToWorld2D(pos, nativeCamera);

                mousePosition = Vector2.fromNative(pos);
                mouseWorldPosition = Vector2.fromNative(posWS);

                pos.close();
                posWS.close();
            }

            // We'll want to pass this down for cool time effects.
            float deltaTime = Raylib.GetFrameTime();

            // Update.
            dungeon.update(deltaTime);
            ProjectileSystem.update(deltaTime);

            // Draw.
            BeginDrawing();
            ClearBackground(BLACK);

            BeginMode2D(nativeCamera);
            dungeon.draw();
            ProjectileSystem.draw();

            EndMode2D();

            EndDrawing();
        }
        CloseWindow();
    }
}
