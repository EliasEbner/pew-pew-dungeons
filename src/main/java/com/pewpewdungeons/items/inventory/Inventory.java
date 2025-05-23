package com.pewpewdungeons.items.inventory;

import com.pewpewdungeons.items.Weapon;
import java.util.List;

public abstract class Inventory {

    protected List<Weapon> weapons; // TODO: Update to use generics / different weapon types
    protected long coins;

    public Inventory(List<Weapon> weapons, long coins) {
        this.weapons = weapons;
        this.coins = coins;
    }
}
