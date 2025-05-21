package com.pewpewdungeons.world;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.collider.Collider;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.entities.Updatable;
import java.util.List;

public final class Dungeon extends GameObject {
    private final List<Room> rooms;
    private Player player;

    public Dungeon(List<Room> rooms) {
        this.rooms = rooms;
        this.createPlayer();
    }

    public void createPlayer() {
        this.player = new Player(this, 100, 100, new Vector2(12, 12), new Vector2(1, 1), 4);
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

    public boolean contains(RectangleCollider collider) {
        boolean[] cornerCollisions = { false, false, false, false };

        for (Room room : this.rooms) {
            for (RectangleCollider roomCollider : room.getColliders()) {
                if (!cornerCollisions[0] && roomCollider.contains(collider.position)) {
                    cornerCollisions[0] = true;
                }
                if (!cornerCollisions[1]
                        && roomCollider
                                .contains(new Vector2(collider.position.x + collider.size.x, collider.position.y))) {
                    cornerCollisions[1] = true;
                }
                if (!cornerCollisions[2]
                        && roomCollider
                                .contains(new Vector2(collider.position.x + collider.size.x,
                                        collider.position.y + collider.size.y))) {
                    cornerCollisions[2] = true;
                }
                if (!cornerCollisions[3]
                        && roomCollider
                                .contains(new Vector2(collider.position.x, collider.position.y + collider.size.y))) {
                    cornerCollisions[3] = true;
                }
            }
        }

        for (boolean cornerCollision : cornerCollisions) {
            if (!cornerCollision) {
                return false;
            }
        }

        return true;
    }
}
