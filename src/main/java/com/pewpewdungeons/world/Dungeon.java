package com.pewpewdungeons.world;

import com.pewpewdungeons.core.Drawable;
import com.pewpewdungeons.core.Updatable;
import com.pewpewdungeons.entities.GameObject;

import java.util.ArrayList;
import java.util.List;

public final class Dungeon implements Drawable, Updatable {

    private final List<GameObject> objects = new ArrayList<>();

    public void add(GameObject go) { objects.add(go); }

    // ───────────────── engine hooks ──────────────────
    @Override public void update()            { objects.forEach(GameObject::update); }
    @Override public void draw()     { objects.forEach(Drawable::draw); }

    // ───────────────── gameplay helpers ──────────────
    public List<GameObject> getObjects()      { return List.copyOf(objects); }
}
