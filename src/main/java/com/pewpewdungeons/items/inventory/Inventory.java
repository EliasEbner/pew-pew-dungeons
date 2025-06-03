package com.pewpewdungeons.items.inventory;

import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.entities.Updatable;
import com.pewpewdungeons.items.Weapon;
import java.util.List;

public abstract class Inventory implements Drawable, Updatable {

    protected List<Weapon> weapons; 
    protected long coins;

    public Inventory(List<Weapon> weapons, long coins) {
        this.weapons = weapons;
        this.coins = coins;
    }
}
