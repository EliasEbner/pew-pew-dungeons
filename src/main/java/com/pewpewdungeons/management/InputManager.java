package com.pewpewdungeons.management;

import java.util.HashSet;
import java.util.Set;

import com.pewpewdungeons.logging.GameLogger;

public class InputManager {
    private static InputManager instance;

    private Set<Integer> keysDown = new HashSet<>();
    private Set<Integer> keysPressed = new HashSet<>();
    private Set<Integer> keysReleased = new HashSet<>();

    private Set<Integer> mouseButtonsDown = new HashSet<>();
    private Set<Integer> mouseButtonsPressed = new HashSet<>();

    private InputManager() {}

    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
            GameLogger.Input.info("InputManager instance created");
        }
        return instance;
    }

    public void update() {
        // This method will be called from Main to update the state
        keysPressed.clear();
        keysReleased.clear();
        mouseButtonsPressed.clear();
    }

    // --- Keyboard ---

    public void setKeyDown(int key) {
        if (!keysDown.contains(key)) {
            keysPressed.add(key);
        }
        keysDown.add(key);
    }

    public void setKeyUp(int key) {
        keysDown.remove(key);
        keysReleased.add(key);
    }

    public boolean isKeyDown(int key) {
        return keysDown.contains(key);
    }

    public boolean isKeyPressed(int key) {
        return keysPressed.contains(key);
    }

    public boolean isKeyReleased(int key) {
        return keysReleased.contains(key);
    }

    // --- Mouse ---
    public void setMouseButtonDown(int button) {
        if (!mouseButtonsDown.contains(button)) {
            mouseButtonsPressed.add(button);
        }
        mouseButtonsDown.add(button);
    }

    public void setMouseButtonUp(int button) {
        mouseButtonsDown.remove(button);
    }

    public boolean isMouseButtonDown(int button) {
        return mouseButtonsDown.contains(button);
    }

    public boolean isMouseButtonPressed(int button) {
        return mouseButtonsPressed.contains(button);
    }
} 