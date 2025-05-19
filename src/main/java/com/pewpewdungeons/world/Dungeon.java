package com.pewpewdungeons.world;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.collider.Collider;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.entities.Updatable;
import java.util.List;

public final class Dungeon extends GameObject {

    private final List<Room> rooms;
    private Player player;

    private Collider collider;

    public Dungeon(List<Room> rooms) {
        this.rooms = rooms;
        this.createPlayer();
    }

    public void createPlayer() {
        this.player = new Player(this, 100, 100, new Vector2(0, 0), new Vector2(1, 1), 4);
    }

    // ───────────────── engine hooks ──────────────────
    @Override
    public void update(float deltaTime) {
        this.rooms.forEach(room -> room.update(deltaTime));
        this.player.update(deltaTime);
    }

    @Override
    public void draw() {
        this.rooms.forEach(Room::draw);
        this.player.draw();
    }

    // ───────────────── gameplay helpers ──────────────
    public List<Room> getRooms() {
        return List.copyOf(this.rooms);
    }
}
