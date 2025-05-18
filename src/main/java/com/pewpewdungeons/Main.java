package com.pewpewdungeons;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;

import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.projectiles.Projectile;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.pewpewdungeons.world.Dungeon;
import com.raylib.Raylib;

public final class Main {

    static Vector2 mousePosition;
    static Vector2 mouseWorldPosition;

    public static Vector2 getMousePosition() { return mousePosition; }
    public static Vector2 getMouseWorldPosition() { return mouseWorldPosition; }

    public static void main(String[] args) {
        int screenWidth = 1280;
        int screenHeight = 720;

        for (int i=0; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals("-screenWidth")) {
                assert i+1 < args.length;
                screenWidth = Integer.parseInt(args[++i]);
            }
            else if (arg.equals("-screenHeight")) {
                assert i+1 < args.length;
                screenHeight = Integer.parseInt(args[++i]);
            }
            else {
                System.out.println("Unknown argument " + arg);
            }
        }

        InitWindow(screenWidth, screenHeight, "Pew-Pew-Dungeons");
        SetTargetFPS(60);

        float screenAspect = (float)screenWidth / (float)screenHeight;
        float viewWidthInWorldSpaceUnits = 40;
        float viewHeightInWorldSpaceUnits = viewWidthInWorldSpaceUnits / screenAspect;

        Dungeon dungeon = new Dungeon();
        Player player = new Player(100, 100, new Vector2(0, 0), new Vector2(1, 1), 4);

        Vector2 cameraOffset = new Vector2((float) screenWidth / 2, (float) screenHeight / 2);
        Vector2 cameraTarget; // in world-space.
        float cameraZoom;

        Camera2D nativeCamera = new Camera2D();

        while (!WindowShouldClose()) {

            cameraTarget = new Vector2(viewWidthInWorldSpaceUnits/2, viewHeightInWorldSpaceUnits/2);
            //cameraTarget = player.getPosition();
            cameraZoom = (float)screenWidth / viewWidthInWorldSpaceUnits;

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

                pos.deallocate();
                posWS.deallocate();
            }

            // We'll want to pass this down for cool time effects.
            float deltaTime = Raylib.GetFrameTime();

            // Update.
            dungeon.update(deltaTime);
            ProjectileSystem.update(deltaTime);
            player.update(deltaTime);


            // Draw.
            BeginDrawing();
            ClearBackground(BLACK);

            BeginMode2D(nativeCamera);

                dungeon.draw();
                ProjectileSystem.draw();
                player.draw();

            EndMode2D();

            EndDrawing();
        }
        CloseWindow();
    }
}
