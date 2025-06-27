package com.pewpewdungeons.projectiles;

import java.util.ArrayList;

import com.pewpewdungeons.logging.GameLogger;
import com.pewpewdungeons.world.Dungeon;

public class ProjectileSystem {

    static ArrayList<Projectile> projectiles = new ArrayList<>();
    static ArrayList<Projectile> projectilesToDelete = new ArrayList<>();
    private static Dungeon dungeon;

    public static void createProjectile(Projectile p) {
        projectiles.add(p);
        GameLogger.Projectiles.debug("Created projectile: {}", p.getClass().getSimpleName());
    }

    public static void removeProjectile(Projectile p) {
        projectilesToDelete.add(p);
        GameLogger.Projectiles.debug("Marked projectile for removal: {}", p.getClass().getSimpleName());
    }

    public static void update(float dt) {
        projectiles.forEach(p -> p.update(dt));
        int removedCount = projectilesToDelete.size();
        projectiles.removeAll(projectilesToDelete);
        projectilesToDelete.clear();
        
        if (removedCount > 0) {
            GameLogger.Projectiles.debug("Removed {} projectiles this frame", removedCount);
        }
        
        // Log projectile count periodically for performance monitoring
        if (System.currentTimeMillis() % 5000 < 16) { // Roughly every 5 seconds
            GameLogger.logEntityCount("projectiles", projectiles.size());
        }
    }

    public static void draw() {
        projectiles.forEach(p -> p.draw());
    }
    
    public static void setDungeon(Dungeon dungeon) {
        ProjectileSystem.dungeon = dungeon;
    }
    
    public static Dungeon getDungeon() {
        return dungeon;
    }
    
    public static void reset() {
        int count = projectiles.size();
        projectiles.clear();
        projectilesToDelete.clear();
        GameLogger.Projectiles.info("Reset projectile system, cleared {} projectiles", count);
    }
}
