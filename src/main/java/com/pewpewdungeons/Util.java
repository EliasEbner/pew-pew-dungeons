package com.pewpewdungeons;

public class Util {

    public static float radiansFromDegrees(float degrees) {
        return (float)(degrees * (180 / Math.PI));
    }

    public static float degreesFromRadians(float radians) {
        return (float)(radians * (Math.PI / 180));
    }
}
