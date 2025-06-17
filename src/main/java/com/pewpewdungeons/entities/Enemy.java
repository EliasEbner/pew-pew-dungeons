package com.pewpewdungeons.entities;

import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.core.AutoMovable;
import com.pewpewdungeons.items.inventory.Inventory;
import com.pewpewdungeons.world.Dungeon;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class Enemy extends GameObject implements AutoMovable {

  protected double health;
  protected Inventory inventory;
  protected Dungeon dungeon;
  protected float attackCooldown = 0;
  protected float attackCooldownMax = 1.0f; // 1 second between attacks
  protected float attackRange = 1.5f;
  protected float attackDamage = 10.0f;
  protected float detectionRange = 8.0f;

  public Enemy(Dungeon dungeon, double health, Vector2 position, Vector2 size, float speed) {
    this.dungeon = dungeon;
    this.health = health;
    this.position = position;
    this.size = size;
    this.speed = speed;
    this.collider = new RectangleCollider(position, size);
  }

  protected boolean canMoveTo(Vector2 newPos) {
    RectangleCollider tempCollider = new RectangleCollider(newPos, size);
    return this.dungeon.contains(tempCollider) && !this.dungeon.collidesWithObjectInRoom(tempCollider);
  }

  @Override
  public void autoMove() {
    Player player = dungeon.getPlayer();
    if (player == null)
      return;

    // Calculate direction to player
    Vector2 direction = new Vector2(player.getPosition());
    direction.sub(this.position);

    // Only move if player is within detection range
    if (direction.length() <= detectionRange) {
      direction.normalize();
      direction.mul(speed * Raylib.GetFrameTime());

      // Try to move
      Vector2 newPos = new Vector2(position);
      newPos.add(direction);
      
      if (canMoveTo(newPos)) {
        this.position = newPos;
        // Update the collider position to match the new position
        this.collider.position = new Vector2(newPos);
      }
    }
  }

  @Override
  public void draw() {
    try (Raylib.Vector2 rPosition = this.position.toNative()) {
      Raylib.DrawRectangleV(rPosition, this.size.toNative(), Jaylib.PURPLE);
    }
  }

  @Override
  public void update(float dt) {
    autoMove();

    // Ensure collider position is in sync with enemy position
    this.collider.position = new Vector2(position);

    // Handle attack cooldown
    if (attackCooldown > 0) {
      attackCooldown -= dt;
    }

    // Check if we can attack player
    Player player = dungeon.getPlayer();
    if (player != null) {
      Vector2 playerPos = player.getPosition();
      float distance = Vector2.distance(position, playerPos);

      if (distance <= attackRange && attackCooldown <= 0) {
        attackPlayer(player);
        attackCooldown = attackCooldownMax;
      }
    }
  }

  protected void attackPlayer(Player player) {
    player.takeDamage(attackDamage);
  }

  public void takeDamage(float damage) {
    health -= damage;
    if (health <= 0) {
      destroy();
    }
  }

  private void destroy() {
    // Add death logic (e.g., drop items)
    dungeon.removeEnemy(this);
  }

  public double getHealth() {
    return health;
  }
}
