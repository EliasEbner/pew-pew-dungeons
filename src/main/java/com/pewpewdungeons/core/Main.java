package com.pewpewdungeons.core;


import static com.raylib.Jaylib.BLACK;
import static com.raylib.Raylib.*;   // all the core_* functions

import com.pewpewdungeons.world.Dungeon;

public final class Main {
    public static void main(String[] args) {
        InitWindow(1280, 720, "Pew-Pew-Dungeons");
        SetTargetFPS(60);

        Dungeon world = new Dungeon();
        GameLoop loop = new GameLoop(world);

        while (!WindowShouldClose()) {
            loop.tick();
            BeginDrawing();
            ClearBackground(BLACK);
            loop.render();
            EndDrawing();
        }
        CloseWindow();
    }
}