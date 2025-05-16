package com.pewpewdungeons.core;

import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*; // all the core_* functions

import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.physics.Vector2;
import com.pewpewdungeons.world.Dungeon;

public final class Main {

    public static void main(String[] args) {
        InitWindow(1280, 720, "Pew-Pew-Dungeons");
        SetTargetFPS(60);

        Dungeon world = new Dungeon();
        GameLoop loop = new GameLoop(world);
        Player player = new Player(100, 100, new Vector2(100, 100), new Vector2(100, 100), 500);

        while (!WindowShouldClose()) {
            loop.tick();
            // logic
            player.update();

            // drawing
            BeginDrawing();
            ClearBackground(BLACK);
            player.draw();
            loop.render();
            EndDrawing();
        }
        CloseWindow();
    }
}
