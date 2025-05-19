package com.pewpewdungeons.items;

import com.pewpewdungeons.Main;
import com.pewpewdungeons.Vector2;
import com.pewpewdungeons.entities.GameObject;
import com.pewpewdungeons.entities.Player;
import com.pewpewdungeons.projectiles.BulletProjectile;
import com.pewpewdungeons.projectiles.Projectile;
import com.pewpewdungeons.projectiles.ProjectileSystem;
import com.raylib.Jaylib;

public class Gun extends GameObject implements RangeWeapon {

    private float distanceFromPlayer;
    private Player player;

    public Gun(Vector2 size, float distanceFromPlayer, Player player) {
        this.size = size;
        this.distanceFromPlayer = distanceFromPlayer;
        this.player = player;
    }

    @Override
    public void shoot() {
        Vector2 pos = new Vector2(player.getCenterPosition());
        Vector2 dir = Main.getMouseWorldPosition();
        dir.sub(player.getCenterPosition());
        dir.normalize();
        dir.mul(20);

        ProjectileSystem.createProjectile(new BulletProjectile(pos, dir));
    }

    @Override
    public void draw() {
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

        Jaylib.DrawRectanglePro(rectangle, origin, rotation, Jaylib.BLUE);

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
