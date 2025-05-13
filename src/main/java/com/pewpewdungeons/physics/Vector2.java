package com.pewpewdungeons.physics;

import com.raylib.Raylib;

/**
 * Immutable 2‑D vector for all gameplay/physics math.
 * <p>
 * Keep this on‑heap (ordinary Java object) and convert to Raylib.Vector2
 * only when talking to the native renderer. That avoids JNI overhead in
 * your hot update loops.
 *
 * Why the native one (com.raylib.Raylib.Vector2) is a edge-only type
 * It’s an off-heap struct that lives in native memory; every field access goes through JNI.
 * Do that thousands of times per tick and you’ll feel it.
 * GitHub
 *
 * The old com.raylib.Jaylib.Vector2 vanished a couple of releases ago, so any docs or snippets that import com.raylib.Jaylib.* are stale.
 */
public record Vector2(float x, float y) {

    // ─────────────────────────── constants ────────────────────────────
    public static final Vector2 ZERO   = new Vector2(0f, 0f);
    public static final Vector2 ONE    = new Vector2(1f, 1f);
    public static final Vector2 UNIT_X = new Vector2(1f, 0f);
    public static final Vector2 UNIT_Y = new Vector2(0f, 1f);

    // ───────────────────────── basic algebra ──────────────────────────
    public Vector2 add(Vector2 v)          { return new Vector2(x + v.x, y + v.y); }
    public Vector2 sub(Vector2 v)          { return new Vector2(x - v.x, y - v.y); }
    public Vector2 mul(float s)            { return new Vector2(x * s, y * s); }
    public Vector2 div(float s)            { return new Vector2(x / s, y / s); }

    // ─────────────────────── vector properties ───────────────────────
    public float lengthSq()                { return x * x + y * y; }
    public float length()                  { return (float) Math.sqrt(lengthSq()); }
    public Vector2 normalized() {
        float len = length();
        return len == 0f ? ZERO : div(len);
    }
    public float dot(Vector2 v)            { return x * v.x + y * v.y; }
    public float distanceSq(Vector2 v)     { return sub(v).lengthSq(); }
    public float distance(Vector2 v)       { return (float) Math.sqrt(distanceSq(v)); }
    public Vector2 clampLength(float max)  {
        float len = length();
        return len > max ? normalized().mul(max) : this;
    }
    public Vector2 lerp(Vector2 to, float t) {
        return new Vector2(x + (to.x - x) * t, y + (to.y - y) * t);
    }

    // ─────────────────────────── conversion ──────────────────────────
    /** Copy to native Raylib struct – call only right before rendering. */
    public Raylib.Vector2 toNative()       { return new Raylib.Vector2().x(x).y(y); }

    /** Build a Vector2 from a Raylib.Vector2 without mutating the source. */
    public static Vector2 fromNative(Raylib.Vector2 v) {
        return new Vector2(v.x(), v.y());
    }
}