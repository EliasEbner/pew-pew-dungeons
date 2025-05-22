package com.pewpewdungeons.projectiles;

import java.util.ArrayList;

import com.pewpewdungeons.world.Dungeon;

public class ProjectileSystem {

    static ArrayList<Projectile> projectiles = new ArrayList<>();
    static ArrayList<Projectile> projectilesToDelete = new ArrayList<>();
    private static Dungeon dungeon;

    public static void createProjectile(Projectile p) {
        projectiles.add(p);
    }

    public static void removeProjectile(Projectile p) {
        projectilesToDelete.add(p);
    }

    public static void update(float dt) {
        projectiles.forEach(p -> p.update(dt));
        projectiles.removeAll(projectilesToDelete);
        projectilesToDelete.clear();
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
        projectiles.clear();
        projectilesToDelete.clear();
    }
}
