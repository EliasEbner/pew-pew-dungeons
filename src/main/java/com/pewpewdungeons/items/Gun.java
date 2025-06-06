package com.pewpewdungeons.items;

import static com.raylib.Raylib.GetTime;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.projectiles.BulletProjectile;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class Gun extends Weapon {

    protected float distanceFromPlayer;
    protected Player player;
    protected Raylib.Color color = Jaylib.BLUE;
    protected double shootingDelay = 0.2;
    protected double lastShotTime = GetTime();

    public Gun(Vector2 size, float distanceFromPlayer, Player player) {
        this.size = size;
        this.distanceFromPlayer = distanceFromPlayer;
        this.player = player;
    }

    protected boolean canShoot() {
        return GetTime() > lastShotTime + shootingDelay;
    }

    @Override
    public void shoot() {
        if(!canShoot()) return;
        Vector2 pos = new Vector2(player.getCenterPosition());
        Vector2 dir = Main.getMouseWorldPosition();
        dir.sub(player.getCenterPosition());
        dir.normalize();
        dir.mul(20);

        ProjectileSystem.createProjectile(new BulletProjectile(pos, dir));
        lastShotTime = GetTime();
    }

    @Override
    public void draw() {
        if(orientation == null) orientation = new Vector2();
        float rotation = (float) Math.toDegrees(Math.atan2(orientation.y, orientation.x));

        Vector2 position = player.getCenterPosition();

        Jaylib.Rectangle rectangle = new Jaylib.Rectangle();
        rectangle.x(position.x + this.orientation.x * this.distanceFromPlayer);
        rectangle.y(position.y + this.orientation.y * this.distanceFromPlayer);
        rectangle.width(this.size.x);
        rectangle.height(this.size.y);

        Jaylib.Vector2 origin = new Jaylib.Vector2();
        origin.x(this.size.x / 2);
        origin.y(this.size.y / 2);

        Jaylib.DrawRectanglePro(rectangle, origin, rotation, color);

        origin.close();
        rectangle.close();
    }

    @Override
    public void update(float dt) {
        orientation = Main.getMouseWorldPosition();
        orientation.sub(player.getCenterPosition());
        orientation.normalize();
    }
}
