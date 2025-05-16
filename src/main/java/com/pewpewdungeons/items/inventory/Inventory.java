package com.pewpewdungeons.items.inventory;

import com.pewpewdungeons.items.RangeWeapon;
import java.util.List;

public abstract class Inventory {

    protected List<RangeWeapon> weapons; // TODO: Update to use generics / different weapon types
    protected long coins;

    public Inventory(List<RangeWeapon> weapons, long coins) {
        this.weapons = weapons;
        this.coins = coins;
    }
}
