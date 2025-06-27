package com.pewpewdungeons.items.inventory;

import com.pewpewdungeons.items.Weapon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class PlayerInventoryTest {

    private PlayerInventory inventory;
    private List<Weapon> weapons;

    @BeforeEach
    void setUp() {
        weapons = new ArrayList<>();
        // Create inventory with empty weapon list and 100 coins
        inventory = new PlayerInventory(weapons, 100L);
    }

    @Test
    void testConstructor() {
        assertNotNull(inventory);
        assertEquals(weapons, inventory.weapons);
        assertEquals(100L, inventory.coins);
    }

    @Test
    void testInventoryToggle() {
        // Initially inventory should be closed
        // Since isOpen is private, we test behavir indirectly through the use() method
        
        // First use should open inventory
        inventory.use();
        
        // Second use should close inventory
        inventory.use();
        
        // We can't directly test isOpen since it's private, but we can verify 
        // the method executes without throwing exceptions
        assertDoesNotThrow(() -> inventory.use());
    }

    @Test
    void testInventoryWithWeapons() {
        // Create a mock weapon list with some items
        List<Weapon> weaponList = new ArrayList<>();
        // Note: We can't instantiate actual Wepon objects easily since they're abstract
        // In a real scenario, you'd use mock objects or concrete implementations
        
        PlayerInventory inventoryWithWeapons = new PlayerInventory(weaponList, 50L);
        assertNotNull(inventoryWithWeapons);
        assertEquals(weaponList, inventoryWithWeapons.weapons);
        assertEquals(50L, inventoryWithWeapons.coins);
    }

    @Test
    void testInventoryWithZeroCoins() {
        PlayerInventory poorInventory = new PlayerInventory(new ArrayList<>(), 0L);
        assertEquals(0L, poorInventory.coins);
    }

    @Test
    void testInventoryWithNegativeCoins() {
        // Test edge case with negative coins (debt)
        PlayerInventory debtInventory = new PlayerInventory(new ArrayList<>(), -10L);
        assertEquals(-10L, debtInventory.coins);
    }

    @Test
    void testUpdateMethod() {
        // Test that update method executes without errors
        float deltaTime = 0.016f; // ~60 FPS
        assertDoesNotThrow(() -> inventory.update(deltaTime));
        
        // Test with different delta times
        assertDoesNotThrow(() -> inventory.update(0.0f));
        assertDoesNotThrow(() -> inventory.update(1.0f));
    }

    @Test
    void testDrawMethodSafety() {
        // For now, we justverify the draw method exists and can be called
        // when the inventory is closed (which should return early and not crash)
        
        // This should be safe because when isOpen=false, draw() returns early
        assertDoesNotThrow(() -> inventory.draw());
    }
} 