package com.pewpewdungeons.items;

import static com.raylib.Raylib.GetTime;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.logging.GameLogger;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.projectiles.BulletProjectile;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.raylib.Jaylib;

public class BurstGun extends Gun {

    // Burst config
    private long interval = 50;
    private boolean isFiringBurst = false;
    private int burstCount = 3;

    public BurstGun(Vector2 size, float distanceFromPlayer, Player player) {
        super(size, distanceFromPlayer, player);
        color = Jaylib.RED;
        shootingDelay = 0.5;
    }

    @Override
    public void shoot() {
        if(isFiringBurst || !canShoot()) return; // Prevent overlapping bursts

        GameLogger.logWeaponEvent("BurstGun", "Starting burst fire");
        isFiringBurst = true;

        new Thread(() -> {
            try {
                for(int i = 0; i < burstCount; i++) {
                    Vector2 pos = new Vector2(player.getCenterPosition());
                    Vector2 dir = Main.getMouseWorldPosition();
                    dir.sub(player.getCenterPosition());
                    dir.normalize();
                    dir.mul(20);

                    ProjectileSystem.createProjectile(new BulletProjectile(pos, dir));

                    if(i < burstCount - 1) {
                        Thread.sleep(interval); 
                    }
                }
            } catch (InterruptedException e) {
                GameLogger.error("BurstGun firing thread interrupted", e);
                Thread.currentThread().interrupt(); // Restore interrupted status
            } finally {
                isFiringBurst = false;
                lastShotTime = GetTime();
            }
        }).start();
    }
}
