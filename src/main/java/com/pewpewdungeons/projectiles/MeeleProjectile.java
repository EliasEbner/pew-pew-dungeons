package com.pewpewdungeons.projectiles;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.logging.GameLogger;
import com.pewpewdungeons.collider.CircleCollider;
import com.pewpewdungeons.collider.RectangleCollider;
import com.pewpewdungeons.entities.Enemy;
import com.pewpewdungeons.entities.Player;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class MeeleProjectile extends Projectile {

    private Player player;
    private Vector2 size;
    private float damage = 25.0f;
    private float radius;           
    private float duration = 0.3f;
    private float timeAlive = 0f;
    private boolean hasHit = false;

    private RectangleCollider collider;

    private float angle = 0f;       // current angle in radians
    private float sweepSpeed = (float)(Math.PI * 2 / duration); // full circle over duration

    public MeeleProjectile(Player player, Vector2 size, float radius) {
        super(new Vector2());
        this.player = player;
        this.size = new Vector2(size);
        this.radius = radius;

        // Start angle facing the mouse cursor
        Vector2 direction = Main.getMouseWorldPosition();
        direction.sub(player.getCenterPosition());
        this.angle = direction.angle(direction);

        // Initial position and collider
        Vector2 playerPos = player.getCenterPosition();
        Vector2 offset = Vector2.fromAngle(angle);
        offset.mul(radius);
        Vector2 centerPos = new Vector2(playerPos);
        centerPos.add(offset);
        this.position = new Vector2(centerPos);

        // Collider top-left position
        Vector2 topLeft = new Vector2(centerPos.x - size.x / 2, centerPos.y - size.y / 2);
        this.collider = new RectangleCollider(topLeft, size);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        timeAlive += dt;
        if (hasHit || timeAlive > duration) {
            if (timeAlive > duration) {
                GameLogger.Projectiles.debug("MeeleProjectile expired after {} seconds", duration);
            }
            ProjectileSystem.removeProjectile(this);
            return;
        }

        // Update angle (swing clockwise)
        angle += sweepSpeed * dt;

        // Calculate new position
        Vector2 playerPos = player.getCenterPosition();
        Vector2 offset = Vector2.fromAngle(angle);
        offset.mul(radius);
        Vector2 centerPos = new Vector2(playerPos);
        centerPos.add(offset);

        this.position.set(centerPos);

        // Update collider top-left
        collider.position.set(centerPos.x - size.x / 2, centerPos.y - size.y / 2);

        // Collision check
        for (Enemy enemy : ProjectileSystem.getDungeon().getEnemies()) {
            if (collider.collide(enemy.getCollider())) {
                enemy.takeDamage(damage);
                hasHit = true;
                GameLogger.logProjectileEvent("MeeleProjectile", "Enemy slashed for " + damage + " damage");
                ProjectileSystem.removeProjectile(this);
                break;
            }
        }
    }

    @Override
    public void draw() {
        Raylib.Rectangle rect = new Raylib.Rectangle();
        rect.x(position.x - size.x / 2);
        rect.y(position.y - size.y / 2);
        rect.width(size.x);
        rect.height(size.y);

        Raylib.Vector2 origin = new Raylib.Vector2();
        origin.x(size.x / 2);
        origin.y(size.y / 2);

        // Draw rectangle rotated according to current angle (degrees)
        float angleDeg = (float) Math.toDegrees(angle) + 90;
        Raylib.DrawRectanglePro(rect, origin, angleDeg, Jaylib.BLUE);

        origin.close();
        rect.close();
    }
}
