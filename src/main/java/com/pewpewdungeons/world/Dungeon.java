package com.pewpewdungeons.world;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.collider.Collider;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.entities.Enemy;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.entities.Updatable;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class Dungeon extends GameObject {
  private final List<Room> rooms;
  private List<Enemy> enemies = new ArrayList<>();
  private Player player;
  private boolean gameWon = false;
  private Vector2 exitPosition;
  private Vector2 exitSize = new Vector2(1.0f, 1.0f);
  private boolean exitReached = false;
  private Random random = new Random();

  public Dungeon(List<Room> rooms) {
    this.rooms = rooms;
    this.createPlayer();
    this.setupExit();
    this.spawnEnemies();
  }

  public void createPlayer() {
    // Create player in the first room
    Room startRoom = rooms.get(0);
    Vector2 roomCenter = new Vector2(
        startRoom.getPosition().x + startRoom.getSize().x / 2,
        startRoom.getPosition().y + startRoom.getSize().y / 2);

    this.player = new Player(this, 100, 100, roomCenter, new Vector2(1, 1), 4);
  }

  private void setupExit() {
    // Place exit in last room
    Room exitRoom = rooms.get(rooms.size() - 1);
    exitPosition = new Vector2(
        exitRoom.getPosition().x + exitRoom.getSize().x / 2,
        exitRoom.getPosition().y + exitRoom.getSize().y / 2);
  }

  private void spawnEnemies() {
    // Skip first room (player spawn)
    for (int i = 1; i < rooms.size(); i++) {
      Room room = rooms.get(i);

      // Number of enemies based on room index (more enemies in later rooms)
      int enemyCount = 1 + i / 2;

      for (int j = 0; j < enemyCount; j++) {
        // Place enemy at random position within room
        float x = room.getPosition().x + random.nextFloat() * room.getSize().x;
        float y = room.getPosition().y + random.nextFloat() * room.getSize().y;

        Vector2 enemyPos = new Vector2(x, y);
        Enemy enemy = new Enemy(this, 50, enemyPos, new Vector2(1, 1), 2.0f + (i * 0.2f));

        enemies.add(enemy);
      }
    }
  }

  // ───────────────── engine hooks ──────────────────
  @Override
  public void update(float deltaTime) {
    this.rooms.forEach(room -> room.update(deltaTime));
    this.player.update(deltaTime);

    // Update all enemies
    for (Enemy enemy : enemies) {
      enemy.update(deltaTime);
    }

    // Check if player reached the exit
    if (!exitReached && !player.isDead()) {
      Vector2 playerPos = player.getPosition();
      float distance = Vector2.distance(playerPos, exitPosition);

      if (distance < 1.5f) {
        exitReached = true;
        gameWon = true;
        System.out.println("You win! Escaped the dungeon!");
      }
    }
  }

  @Override
  public void draw() {
    this.rooms.forEach(Room::draw);

    // Draw exit
    try (Raylib.Vector2 exitPos = this.exitPosition.toNative()) {
      Raylib.DrawRectangleV(exitPos, this.exitSize.toNative(), Jaylib.GOLD);
    }

    // Draw all enemies
    for (Enemy enemy : enemies) {
      enemy.draw();
    }

    this.player.draw();

    // Draw game over or win message
    if (player.isDead()) {
      drawCenteredText("GAME OVER", 40, Jaylib.RED);
    } else if (gameWon) {
      drawCenteredText("YOU WIN!", 40, Jaylib.GREEN);
    }
  }

  private void drawCenteredText(String text, int fontSize, Raylib.Color color) {
    int screenWidth = Raylib.GetScreenWidth();
    int screenHeight = Raylib.GetScreenHeight();
    Raylib.Vector2 measure = Raylib.MeasureTextEx(Raylib.GetFontDefault(), text, fontSize, 1);

    float x = (screenWidth - measure.x()) / 2;
    float y = (screenHeight - measure.y()) / 2;

    Raylib.DrawText(text, (int) x, (int) y, fontSize, color);

    measure.close();
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

  public void removeEnemy(Enemy enemy) {
    enemies.remove(enemy);
  }

  public Player getPlayer() {
    return player;
  }

  public List<Enemy> getEnemies() {
    return new ArrayList<>(enemies);
  }
}
