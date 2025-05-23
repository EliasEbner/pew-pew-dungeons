package com.pewpewdungeons.items.inventory;

import com.pewpewdungeons.items.Weapon;
import java.util.List;

public class PlayerInventory extends Inventory {

    public PlayerInventory(List<Weapon> weapons, long coins) {
        super(weapons, coins);
    }
}
