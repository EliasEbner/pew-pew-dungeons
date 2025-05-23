package com.pewpewdungeons;

import static com.raylib.Jaylib.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.pewpewdungeons.collider.CircleCollider;
import com.pewpewdungeons.collider.Collider;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.enums.DoorPositionEnum;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.pewpewdungeons.world.Dungeon;
import com.pewpewdungeons.world.DungeonGenerator;
import com.pewpewdungeons.world.Room;
import com.raylib.Raylib;

public final class Main {

    static Vector2 mousePosition;
    static Vector2 mouseWorldPosition;

    // Only for Debug
    public static List<String> debugOutput = new LinkedList<>();

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

        // Generate a dungeon with 5 rooms
        DungeonGenerator generator = new DungeonGenerator();
        Dungeon dungeon = generator.generateDungeon(5);
        
        // Set the dungeon in the ProjectileSystem
        ProjectileSystem.setDungeon(dungeon);
        
        Vector2 cameraOffset = new Vector2((float) screenWidth / 2, (float) screenHeight / 2);
        Vector2 cameraTarget; // in world-space.
        float cameraZoom;

        Camera2D nativeCamera = new Camera2D();

        while (!WindowShouldClose()) {
            // Get player position for camera centering
            Player player = dungeon.getPlayer();
            if (player != null) {
                cameraTarget = player.getPosition();
            } else {
                cameraTarget = new Vector2(viewWidthInWorldSpaceUnits / 2, viewHeightInWorldSpaceUnits / 2);
            }
            
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

            // Check for restart
            if (Raylib.IsKeyPressed(KEY_R)) {
                // Generate new dungeon
                dungeon = generator.generateDungeon(5);
                ProjectileSystem.reset();
                ProjectileSystem.setDungeon(dungeon);
            }

            // Draw.
            BeginDrawing();
            ClearBackground(BLACK);

            BeginMode2D(nativeCamera);
            dungeon.draw();
            ProjectileSystem.draw();

            EndMode2D();

            // Draw game instructions
            DrawText("WASD: Move", 10, 10, 20, WHITE);
            DrawText("Left Mouse Button: Shoot", 10, 40, 20, WHITE);
            DrawText("R: Restart", 10, 70, 20, WHITE);

            // Debug Output
            while(debugOutput.size() > 5)
                debugOutput.removeFirst();
            for(int i = 0; i < debugOutput.size(); i++)
                DrawText("Debug: " + debugOutput.get(i), 10, 120 + i*10, 10, RED);

            EndDrawing();
        }
        CloseWindow();
    }
}
