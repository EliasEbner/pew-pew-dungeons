package com.pewpewdungeons;

public class Util {

    public static float radiansFromDegrees(float degrees) {
        return (float)(degrees * (Math.PI / 180));
    }

    public static float degreesFromRadians(float radians) {
        return (float)(radians * (180 / Math.PI));
    }
}
