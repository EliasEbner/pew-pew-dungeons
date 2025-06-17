package com.pewpewdungeons;

import com.pewpewdungeons.entities.Player;
import static com.raylib.Jaylib.BLACK;
import static com.raylib.Jaylib.RED;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.BeginMode2D;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.CloseWindow;
import static com.raylib.Raylib.DrawText;
import static com.raylib.Raylib.EndDrawing;
import static com.raylib.Raylib.EndMode2D;
import static com.raylib.Raylib.GetScreenToWorld2D;
import static com.raylib.Raylib.InitWindow;
import static com.raylib.Raylib.IsKeyDown;
import static com.raylib.Raylib.IsKeyPressed;
import static com.raylib.Raylib.IsKeyReleased;
import static com.raylib.Raylib.IsMouseButtonDown;
import static com.raylib.Raylib.IsMouseButtonPressed;
import static com.raylib.Raylib.KEY_A;
import static com.raylib.Raylib.KEY_D;
import static com.raylib.Raylib.KEY_E;
import static com.raylib.Raylib.KEY_ONE;
import static com.raylib.Raylib.KEY_R;
import static com.raylib.Raylib.KEY_S;
import static com.raylib.Raylib.KEY_THREE;
import static com.raylib.Raylib.KEY_TWO;
import static com.raylib.Raylib.KEY_W;
import static com.raylib.Raylib.MOUSE_BUTTON_LEFT;
import static com.raylib.Raylib.SetTargetFPS;
import static com.raylib.Raylib.WindowShouldClose;

import com.pewpewdungeons.management.InputManager;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.pewpewdungeons.world.Dungeon;
import com.pewpewdungeons.world.DungeonGenerator;
import com.raylib.Raylib;
import com.raylib.Raylib.Camera2D;
import java.util.LinkedList;
import java.util.List;
import java.util.LinkedList;

public final class Main {

    static Vector2 mousePosition;
    static Vector2 mouseWorldPosition;

    static int screenWidth = 1280;
    static int screenHeight = 720;

    // Only for Debug
    public static List<String> debugOutput = new LinkedList<>();

    public static Vector2 getMousePosition() {
        return mousePosition;
    }

    public static Vector2 getMouseWorldPosition() {
        return new Vector2(mouseWorldPosition);
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    private static void updateInputs(InputManager inputManager) {
        // --- Keyboard ---
        int[] keys = { KEY_A, KEY_D, KEY_W, KEY_S, KEY_ONE, KEY_TWO, KEY_THREE, KEY_E, KEY_R };
        for (int key : keys) {
            if (IsKeyDown(key)) {
                inputManager.setKeyDown(key);
            } else {
                inputManager.setKeyUp(key);
            }
        }
        // Pressed is not handled by raylib in a stateful way, so we check it manually
        if (IsKeyPressed(KEY_R)) {
            inputManager.setKeyDown(KEY_R);
        }
        if(IsKeyReleased(KEY_E))
        {
            inputManager.setKeyUp(KEY_E);
        }


        // --- Mouse ---
        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            inputManager.setMouseButtonDown(MOUSE_BUTTON_LEFT);
        } else {
            inputManager.setMouseButtonUp(MOUSE_BUTTON_LEFT);
        }
        if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT))
        {
            inputManager.setMouseButtonDown(MOUSE_BUTTON_LEFT);
        }
    }

    public static void main(String[] args) {

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
        Dungeon dungeon = DungeonGenerator.generateDungeon(5);

        // Set the dungeon in the ProjectileSystem
        ProjectileSystem.setDungeon(dungeon);

        InputManager inputManager = InputManager.getInstance();

        Vector2 cameraOffset = new Vector2((float) screenWidth / 2, (float) screenHeight / 2);
        Vector2 cameraTarget; // in world-space.
        float cameraZoom;

        Camera2D nativeCamera = new Camera2D();

        while (!WindowShouldClose()) {
            inputManager.update();
            updateInputs(inputManager);

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
            if (inputManager.isKeyPressed(KEY_R)) {
                // Generate new dungeon
                dungeon = DungeonGenerator.generateDungeon(5);
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
            while (debugOutput.size() > 5)
                debugOutput.removeFirst();
            for (int i = 0; i < debugOutput.size(); i++)
                DrawText("Debug: " + debugOutput.get(i), 10, 120 + i * 10, 10, RED);

            EndDrawing();
        }
        CloseWindow();
    }
}
