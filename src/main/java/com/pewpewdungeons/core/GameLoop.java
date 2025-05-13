package com.pewpewdungeons.core;

import com.raylib.Raylib;

public class GameLoop {
    private final Updatable root;

    public GameLoop(Updatable root) {
        this.root = root;
    }

    public void tick() {
        root.update();
    }

    public void render() {
        if (root instanceof Drawable d) d.draw();
    }
}