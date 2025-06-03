package com.pewpewdungeons.items.inventory;

import com.pewpewdungeons.items.Weapon;
import java.util.List;
import com.raylib.Raylib;
import com.raylib.Jaylib;
import com.raylib.Raylib.Rectangle;
import com.pewpewdungeons.Main;

public class PlayerInventory extends Inventory {
    // TODO: add other items apart from weapons
    private boolean isOpen = false;
    
    public PlayerInventory(List<Weapon> weapons, long coins) {
        super(weapons, coins);
    }

    public void use() {
        isOpen = !isOpen;
    }

    @Override
    public void draw() {
        if(!isOpen) return;

        float x = Main.getScreenWidth();
        float y = Main.getScreenHeight();
        Rectangle background = new Rectangle()
            .x(x / 4)
            .y(y / 4)
            .width(x * 2)
            .height(y * 2);
        Raylib.DrawRectangleRounded(background, 0.8f, 10, Jaylib.WHITE);

        background.close();
    }

    @Override
    public void update(float dt) {
    }
}
