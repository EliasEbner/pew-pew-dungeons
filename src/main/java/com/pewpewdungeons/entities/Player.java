package com.pewpewdungeons.entities;

import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.items.*;
import com.pewpewdungeons.items.inventory.PlayerInventory;
import com.pewpewdungeons.world.Dungeon;
import com.pewpewdungeons.Main;
import com.pewpewdungeons.Vector2;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import java.util.ArrayList;

import static com.raylib.Raylib.*;

public class Player extends GameObject {
  private Dungeon dungeon;

  private double health;
  private double maxHealth;
  private double mana;
  private PlayerInventory inventory;
  private Weapon equippedWeapon = new ShotGun(new Vector2(0.5f, 0.25f), 1.2f, this);
  private boolean isDead = false;
  private float invulnerabilityTime = 0; // Time player is invulnerable after taking damage
  private float invulnerabilityDuration = 1.0f; // 1 second of invulnerability

  public Player(Dungeon dungeon, double health, double mana, Vector2 position, Vector2 size, float speed) {
    this.health = health;
    this.maxHealth = health;
    this.mana = mana;
    this.position = position;
    this.size = size;
    this.speed = speed;
    this.inventory = new PlayerInventory(new ArrayList<Weapon>(), 0);

    this.collider = new RectangleCollider(position, size);
    this.dungeon = dungeon;
  }

  @Override
  public void draw() {
    try (Raylib.Vector2 rPosition = this.position.toNative()) {
      // Flash player red when invulnerable
      if (invulnerabilityTime > 0 && ((int) (invulnerabilityTime * 10) % 2 == 0)) {
        Raylib.DrawRectangleV(rPosition, this.size.toNative(), Jaylib.WHITE);
      } else {
        Raylib.DrawRectangleV(rPosition, this.size.toNative(), Jaylib.RED);
      }
    }
    if (equippedWeapon != null)
      if (equippedWeapon instanceof Drawable d)
        d.draw();

    // Draw health bar above player
    Vector2 healthBarPos = new Vector2(position.x, position.y - 0.5f);
    Vector2 healthBarSize = new Vector2(size.x, 0.2f);

    // Health bar background
    try (Raylib.Vector2 barPos = healthBarPos.toNative();
        Raylib.Vector2 barSize = healthBarSize.toNative()) {
      Raylib.DrawRectangleV(barPos, barSize, Jaylib.DARKGRAY);
    }

    // Health bar fill
    Vector2 fillSize = new Vector2((float) (healthBarSize.x * (health / maxHealth)), healthBarSize.y);
    try (Raylib.Vector2 barPos = healthBarPos.toNative();
        Raylib.Vector2 barSize = fillSize.toNative()) {
      Raylib.DrawRectangleV(barPos, barSize, Jaylib.GREEN);
    }
  }

  @Override
  public void update(float dt) {
    if (isDead)
      return;

    // Update invulnerability time
    if (invulnerabilityTime > 0) {
      invulnerabilityTime -= dt;
    }

    float dx = 0;
    float dy = 0;
    float v = this.speed * Raylib.GetFrameTime();

    if (Raylib.IsKeyDown(KEY_A))
      dx -= v;
    if (Raylib.IsKeyDown(KEY_D))
      dx += v;

    if (Raylib.IsKeyDown(KEY_W))
      dy -= v;
    if (Raylib.IsKeyDown(KEY_S))
      dy += v;

    RectangleCollider tempCollider = new RectangleCollider(new Vector2(this.position.x + dx, this.position.y + dy),
        this.size);

    if (this.dungeon.contains(tempCollider) && !this.dungeon.collidesWithObjectInRoom(tempCollider)) {
      this.position.add(dx, dy);
    }

    if (Raylib.IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
      Main.debugOutput.add("Shot fired");
      equippedWeapon.shoot();
    }

    if (equippedWeapon != null)
      if (equippedWeapon instanceof Updatable u)
        u.update(dt);
  }

  public void takeDamage(float damage) {
    // Only take damage if not invulnerable
    if (invulnerabilityTime <= 0) {
      health -= damage;
      invulnerabilityTime = invulnerabilityDuration;

      if (health <= 0) {
        die();
      }
    }
  }

  private void die() {
    isDead = true;
    // You could trigger game over screen here
    System.out.println("Player died!");
  }

  public boolean isDead() {
    return isDead;
  }

  public Vector2 getPosition() {
    return new Vector2(position);
  }

  public Vector2 getCenterPosition() {
    return new Vector2(
        position.x + size.x / 2,
        position.y + size.y / 2);
  }

  public double getHealth() {
    return health;
  }

  public void heal(double amount) {
    health = Math.min(maxHealth, health + amount);
  }
}
