package com.pewpewdungeons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testRadiansFromDegrees() {
        // Test common angle conversions
        assertEquals(0.0f, Util.radiansFromDegrees(0.0f), 0.001f);
        assertEquals(Math.PI / 2, Util.radiansFromDegrees(90.0f), 0.001f);
        assertEquals(Math.PI, Util.radiansFromDegrees(180.0f), 0.001f);
        assertEquals(3 * Math.PI / 2, Util.radiansFromDegrees(270.0f), 0.001f);
        assertEquals(2 * Math.PI, Util.radiansFromDegrees(360.0f), 0.001f);
        
        // Test negative angles
        assertEquals(-Math.PI / 2, Util.radiansFromDegrees(-90.0f), 0.001f);
        assertEquals(-Math.PI, Util.radiansFromDegrees(-180.0f), 0.001f);
        
        // Test arbitrary angle
        assertEquals(Math.PI / 4, Util.radiansFromDegrees(45.0f), 0.001f);
    }

    @Test
    void testDegreesFromRadians() {
        // Test common angle conversions
        assertEquals(0.0f, Util.degreesFromRadians(0.0f), 0.001f);
        assertEquals(90.0f, Util.degreesFromRadians((float)(Math.PI / 2)), 0.001f);
        assertEquals(180.0f, Util.degreesFromRadians((float)Math.PI), 0.001f);
        assertEquals(270.0f, Util.degreesFromRadians((float)(3 * Math.PI / 2)), 0.001f);
        assertEquals(360.0f, Util.degreesFromRadians((float)(2 * Math.PI)), 0.001f);
        
        // Test negative angles
        assertEquals(-90.0f, Util.degreesFromRadians((float)(-Math.PI / 2)), 0.001f);
        assertEquals(-180.0f, Util.degreesFromRadians((float)(-Math.PI)), 0.001f);
        
        // Test arbitrary angle
        assertEquals(45.0f, Util.degreesFromRadians((float)(Math.PI / 4)), 0.001f);
    }

    @Test
    void testAngleConversionRoundTrip() {
        // Test that converting degrees to radians and back gives the original value
        float[] testAngles = {0.0f, 30.0f, 45.0f, 60.0f, 90.0f, 120.0f, 180.0f, 270.0f, 360.0f, -90.0f, -180.0f};
        
        for (float degrees : testAngles) {
            float radians = Util.radiansFromDegrees(degrees);
            float backToDegrees = Util.degreesFromRadians(radians);
            assertEquals(degrees, backToDegrees, 0.001f, 
                "Round trip conversion failed for " + degrees + " degrees");
        }
    }

    @Test
    void testAngleConversionPrecision() {
        // Test with very small angles
        float smallAngle = 0.1f;
        float radians = Util.radiansFromDegrees(smallAngle);
        float backToDegrees = Util.degreesFromRadians(radians);
        assertEquals(smallAngle, backToDegrees, 0.0001f);
        
        // Test with very large angles
        float largeAngle = 3600.0f; // 10 full rotations
        float radians2 = Util.radiansFromDegrees(largeAngle);
        float backToDegrees2 = Util.degreesFromRadians(radians2);
        assertEquals(largeAngle, backToDegrees2, 0.001f);
    }
} 