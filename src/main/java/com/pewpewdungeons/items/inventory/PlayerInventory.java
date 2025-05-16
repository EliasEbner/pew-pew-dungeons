package com.pewpewdungeons.items.inventory;

import com.pewpewdungeons.items.RangeWeapon;
import java.util.List;

public class PlayerInventory extends Inventory {

    public PlayerInventory(List<RangeWeapon> weapons, long coins) {
        super(weapons, coins);
    }
}
