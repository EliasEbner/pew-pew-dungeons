package com.pewpewdungeons.items;

import static com.raylib.Raylib.GetTime;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.projectiles.BulletProjectile;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.raylib.Jaylib;

public class ShotGun extends Gun {

    // Shotgun spread configuration
    private int projectileCount = 3;
    private float spreadAngle = 20f; // total spread in degrees

    public ShotGun(Vector2 size, float distanceFromPlayer, Player player) {
        super(size, distanceFromPlayer, player);
        color = Jaylib.GREEN;
        shootingDelay = 0.5;
    }

    @Override
    public void shoot() {
        if(!canShoot()) return;
        Vector2 pos = new Vector2(player.getCenterPosition());
        Vector2 dir = Main.getMouseWorldPosition();
        dir.sub(player.getCenterPosition());
        dir.normalize();
        dir.mul(20);

        for (int i = 0; i < projectileCount; i++) {
            // Calculate the angle offset for each projectile
            float angleOffset = spreadAngle * ((i - (projectileCount - 1) / 2f) / (projectileCount - 1));
            Vector2 spreadDir = dir.rotateVector(angleOffset);
            ProjectileSystem.createProjectile(new BulletProjectile(new Vector2(pos), spreadDir));
        }
        lastShotTime = GetTime();
    }
}
