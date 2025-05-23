package com.pewpewdungeons;

import com.raylib.Raylib;

/**
 * Immutable 2‑D vector for all gameplay/physics math.
 * <p>
 * Keep this on‑heap (ordinary Java object) and convert to Raylib.Vector2
 * only when talking to the native renderer. That avoids JNI overhead in
 * your hot update loops.
 *
 * Why the native one (com.raylib.Raylib.Vector2) is a edge-only type
 * It's an off-heap struct that lives in native memory; every field access goes
 * through JNI.
 * Do that thousands of times per tick and you'll feel it.
 * GitHub
 *
 * The old com.raylib.Jaylib.Vector2 vanished a couple of releases ago, so any
 * docs or snippets that import com.raylib.Jaylib.* are stale.
 */
public class Vector2 {

    public static final Vector2 ZERO = new Vector2(0f, 0f);
    public static final Vector2 ONE = new Vector2(1f, 1f);
    public static final Vector2 X_AXIS = new Vector2(1f, 0f);
    public static final Vector2 Y_AXIS = new Vector2(0f, 1f);

    public float x;
    public float y;

    public Vector2() {
        x = y = 0;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public static float distanceSquared(Vector2 a, Vector2 b) {
        float dx = (b.x - a.x);
        float dy = (b.y - a.y);
        return dx * dx + dy * dy;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void sub(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public void sub(Vector2 v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void mul(float s) {
        this.x *= s;
        this.y *= s;
    }

    public void div(float s) {
        this.x /= s;
        this.y /= s;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public float angle(Vector2 v) {
        return (float) Math.acos(this.dot(v) / (length() * v.length()));
    }

    public void normalize() {
        float len = this.length();
        if (len != 0.0f) {
            this.x /= len;
            this.y /= len;
        }
    }

    public Vector2 normalized() {
        float len = length();
        if (len == 0f)
            return ZERO;
        return new Vector2(this.x / len, this.y / len);
    }

    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }

    public float distanceSq(Vector2 v) {
        float dx = v.x - x;
        float dy = v.y - y;
        return dx * dx + dy * dy;
    }

    public float distance(Vector2 v) {
        return (float) Math.sqrt(distanceSq(v));
    }

    public Vector2 clampLength(float max) {
        float len = length();
        Vector2 normalizedVec = this.normalized();
        normalizedVec.mul(max);

        return len > max ? normalizedVec : this;
    }

    public Vector2 lerp(Vector2 to, float t) {
        return new Vector2(x + (to.x - x) * t, y + (to.y - y) * t);
    }

    public Vector2 rotateVector(float degrees) {
        float radians = (float) Math.toRadians(degrees);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);
        return new Vector2(
            x * cos - y * sin,
            x * sin + y * cos
        );
    }

    // ─────────────────────────── conversion ──────────────────────────
    /** Copy to native Raylib struct – call only right before rendering. */
    public Raylib.Vector2 toNative() {
        return new Raylib.Vector2().x(x).y(y); // MEMORY MUST BE FREED!!!
    }

    /** Build a Vector2 from a Raylib.Vector2 without mutating the source. */
    public static Vector2 fromNative(Raylib.Vector2 v) {
        return new Vector2(v.x(), v.y());
    }

    public static float distance(Vector2 a, Vector2 b) {
        float dx = b.x - a.x;
        float dy = b.y - a.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
