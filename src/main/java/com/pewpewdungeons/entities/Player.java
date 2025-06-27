package com.pewpewdungeons.entities;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.items.*;
import com.pewpewdungeons.items.inventory.PlayerInventory;
import com.pewpewdungeons.management.InputManager;
import com.pewpewdungeons.world.Dungeon;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.raylib.Raylib.*;

public class Player extends GameObject {
    private Dungeon dungeon;

    private double health;
    private double maxHealth;
    private double mana;
    private double maxMana;
    private float manaRegen = 5.0f;
    private PlayerInventory inventory;
    private List<Weapon> weapons;
    private int equippedWeaponIndex = 0;
    private Weapon equippedWeapon;
    private boolean isDead = false;
    private float invulnerabilityTime = 0; // Time player is invulnerable after taking damage
    private float invulnerabilityDuration = 1.0f; // 1 second of invulnerability
    private float minTeleportRange = 10f;
    private float maxTeleportRange = 20f;
    private Random rand = new Random();

    public Player(Dungeon dungeon, double health, double mana, Vector2 position, Vector2 size, float speed) {
        this.health = health;
        this.maxHealth = health;
        this.mana = mana;
        this.maxMana = mana;
        this.position = position;
        this.size = size;
        this.speed = speed;

        weapons = new ArrayList<>();
        weapons.add(new ShotGun(new Vector2(0.5f, 0.25f), 1.2f, this));
        weapons.add(new BurstGun(new Vector2(0.5f, 0.25f), 1.2f, this));
        weapons.add(new Sword(new Vector2(0.5f, 0.25f), 1.2f, this));
        equippedWeapon = weapons.get(equippedWeaponIndex);

        this.inventory = new PlayerInventory(weapons, 0);

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
        Vector2 healthBarPos = new Vector2(position.x, position.y - 0.8f);
        Vector2 healthBarSize = new Vector2(size.x, 0.2f);

        // Health bar background
        try (Raylib.Vector2 barPos = healthBarPos.toNative();
                Raylib.Vector2 barSize = healthBarSize.toNative()) {
            Raylib.DrawRectangleV(barPos, barSize, Jaylib.DARKGRAY);
        }

        // Health bar fill
        Vector2 healthfillSize = new Vector2((float) (healthBarSize.x * (health / maxHealth)), healthBarSize.y);
        try (Raylib.Vector2 barPos = healthBarPos.toNative();
                Raylib.Vector2 barSize = healthfillSize.toNative()) {
            Raylib.DrawRectangleV(barPos, barSize, Jaylib.GREEN);
        }

        // Draw Mana bar above player
        Vector2 manaBarPos = new Vector2(position.x, position.y - 0.5f);
        Vector2 manaBarSize = new Vector2(size.x, 0.2f);

        // Mana bar background
        try (Raylib.Vector2 barPos = manaBarPos.toNative();
                Raylib.Vector2 barSize = manaBarSize.toNative()) {
            Raylib.DrawRectangleV(barPos, barSize, Jaylib.DARKGRAY);
        }

        // Mana bar fill
        Vector2 manafillSize = new Vector2((float) (manaBarSize.x * (mana / maxMana)), manaBarSize.y);
        try (Raylib.Vector2 barPos = manaBarPos.toNative();
                Raylib.Vector2 barSize = manafillSize.toNative()) {
            Raylib.DrawRectangleV(barPos, barSize, Jaylib.BLUE);
        }

        if (inventory != null && inventory instanceof Drawable)
            inventory.draw();
    }

    @Override
    public void update(float dt) {
        if (isDead)
            return;

        InputManager inputManager = InputManager.getInstance();

        // Update invulnerability time
        if (invulnerabilityTime > 0) {
            invulnerabilityTime -= dt;
        }

        float dx = 0;
        float dy = 0;
        float v = this.speed * dt;

        if (inputManager.isKeyDown(KEY_A))
            dx -= v;
        if (inputManager.isKeyDown(KEY_D))
            dx += v;

        if (inputManager.isKeyDown(KEY_W))
            dy -= v;
        if (inputManager.isKeyDown(KEY_S))
            dy += v;

        RectangleCollider tempCollider = new RectangleCollider(new Vector2(this.position.x + dx, this.position.y + dy),
                this.size);

        if (this.dungeon.contains(tempCollider) && !this.dungeon.collidesWithObjectInRoom(tempCollider)) {
            this.position.add(dx, dy);
        }

        if (inputManager.isMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
            Main.debugOutput.add("Shot fired");
            equippedWeapon.shoot();
        }

        // Weapon switching
        if (inputManager.isKeyPressed(KEY_ONE)) {
            switchWeapon(0);
        }
        if (inputManager.isKeyPressed(KEY_TWO)) {
            switchWeapon(1);
        }
        if (inputManager.isKeyPressed(KEY_THREE)) {
            switchWeapon(2);
        }

        if (equippedWeapon != null)
            if (equippedWeapon instanceof Updatable u)
                u.update(dt);

        // Inventory
        if (inputManager.isKeyReleased(KEY_E))
            inventory.use();

        if (inventory instanceof Updatable)
            inventory.update(dt);

        if (inputManager.isKeyPressed(KEY_T))
            teleport();

        // regenerate mana
        if (mana < 100)
            mana += manaRegen * dt;

        // update collider position
        collider.position = this.position;
    }

    private void switchWeapon(int index) {
        if (index >= 0 && index < weapons.size()) {
            equippedWeaponIndex = index;
            equippedWeapon = weapons.get(equippedWeaponIndex);
            Main.debugOutput.add("Switched to weapon " + (index + 1));
        }
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

    private void teleport() {
        if (mana > 70) {
            // try 100 different positions if not working just exit
            for (int i = 0; i < 100; i++) {
                float offsetX = rand.nextFloat(minTeleportRange, maxTeleportRange);
                float offsetY = rand.nextFloat(minTeleportRange, maxTeleportRange);

                if (rand.nextBoolean()) offsetX = -offsetX;
                if (rand.nextBoolean()) offsetY = -offsetY;

                Vector2 newPosition = new Vector2(position.x + offsetX, position.y + offsetY);

                RectangleCollider tempCollider = new RectangleCollider(newPosition, this.size);

                if (this.dungeon.contains(tempCollider) && !this.dungeon.collidesWithObjectInRoom(tempCollider)) {
                    setPosition(newPosition);
                    mana -= 70;
                    break;
                }
            }
        }
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
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

    public PlayerInventory getInventory() {
        return inventory;
    }
}
