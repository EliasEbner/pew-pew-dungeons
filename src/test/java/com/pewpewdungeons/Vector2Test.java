package com.pewpewdungeons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class Vector2Test {

    private Vector2 vectorA;
    private Vector2 vectorB;
    private Vector2 zeroVector;
    private Vector2 oneVector;

    @BeforeEach
    void setUp() {
        vectorA = new Vector2(3.0f, 4.0f);
        vectorB = new Vector2(1.0f, 2.0f);
        zeroVector = new Vector2(0.0f, 0.0f);
        oneVector = new Vector2(1.0f, 1.0f);
    }

    @Test
    void testConstructors() {
        // Test default constructor
        Vector2 defaultVector = new Vector2();
        assertEquals(0.0f, defaultVector.x);
        assertEquals(0.0f, defaultVector.y);

        // Test parameterized constructor
        Vector2 paramVector = new Vector2(5.0f, 7.0f);
        assertEquals(5.0f, paramVector.x);
        assertEquals(7.0f, paramVector.y);

        // Test copy constructor
        Vector2 copyVector = new Vector2(paramVector);
        assertEquals(5.0f, copyVector.x);
        assertEquals(7.0f, copyVector.y);
    }

    @Test
    void testConstants() {
        assertEquals(0.0f, Vector2.ZERO.x);
        assertEquals(0.0f, Vector2.ZERO.y);
        
        assertEquals(1.0f, Vector2.ONE.x);
        assertEquals(1.0f, Vector2.ONE.y);
        
        assertEquals(1.0f, Vector2.X_AXIS.x);
        assertEquals(0.0f, Vector2.X_AXIS.y);
        
        assertEquals(0.0f, Vector2.Y_AXIS.x);
        assertEquals(1.0f, Vector2.Y_AXIS.y);
    }

    @Test
    void testAdd() {
        // Test add with floats
        Vector2 result = new Vector2(vectorA);
        result.add(2.0f, 3.0f);
        assertEquals(5.0f, result.x);
        assertEquals(7.0f, result.y);

        // Test add with Vector2
        Vector2 result2 = new Vector2(vectorA);
        result2.add(vectorB);
        assertEquals(4.0f, result2.x);
        assertEquals(6.0f, result2.y);
    }

    @Test
    void testSubtract() {
        // Test subtract with floats
        Vector2 result = new Vector2(vectorA);
        result.sub(1.0f, 2.0f);
        assertEquals(2.0f, result.x);
        assertEquals(2.0f, result.y);

        // Test subtract with Vector2
        Vector2 result2 = new Vector2(vectorA);
        result2.sub(vectorB);
        assertEquals(2.0f, result2.x);
        assertEquals(2.0f, result2.y);
    }

    @Test
    void testMultiply() {
        Vector2 result = new Vector2(vectorA);
        result.mul(2.0f);
        assertEquals(6.0f, result.x);
        assertEquals(8.0f, result.y);
    }

    @Test
    void testDivide() {
        Vector2 result = new Vector2(vectorA);
        result.div(2.0f);
        assertEquals(1.5f, result.x);
        assertEquals(2.0f, result.y);
    }

    @Test
    void testLength() {
        // Test length of (3, 4) should be 5
        assertEquals(5.0f, vectorA.length(), 0.001f);
        assertEquals(25.0f, vectorA.lengthSquared(), 0.001f);
        
        // Test zero vector
        assertEquals(0.0f, zeroVector.length());
        assertEquals(0.0f, zeroVector.lengthSquared());
    }

    @Test
    void testDistance() {
        // Distance between (3,4) and (1,2) should be sqrt((3-1)^2 + (4-2)^2) = sqrt(8)
        float expectedDistance = (float) Math.sqrt(8);
        assertEquals(expectedDistance, vectorA.distance(vectorB), 0.001f);
        assertEquals(8.0f, vectorA.distanceSq(vectorB), 0.001f);
        
        // Static method test
        assertEquals(expectedDistance, Vector2.distance(vectorA, vectorB), 0.001f);
        assertEquals(8.0f, Vector2.distanceSquared(vectorA, vectorB), 0.001f);
    }

    @Test
    void testNormalize() {
        Vector2 normalizedA = vectorA.normalized();
        assertEquals(1.0f, normalizedA.length(), 0.001f);
        assertEquals(0.6f, normalizedA.x, 0.001f);
        assertEquals(0.8f, normalizedA.y, 0.001f);

        // Test in-place normalization
        Vector2 toNormalize = new Vector2(vectorA);
        toNormalize.normalize();
        assertEquals(1.0f, toNormalize.length(), 0.001f);
        
        // Test normalizing zero vector
        Vector2 normalizedZero = zeroVector.normalized();
        assertEquals(Vector2.ZERO.x, normalizedZero.x);
        assertEquals(Vector2.ZERO.y, normalizedZero.y);
    }

    @Test
    void testDotProduct() {
        // Dot product of (3,4) and (1,2) should be 3*1 + 4*2 = 11
        assertEquals(11.0f, vectorA.dot(vectorB), 0.001f);
        
        // Dot product with itself equals length squared
        assertEquals(vectorA.lengthSquared(), vectorA.dot(vectorA), 0.001f);
        
        // Dot product with zero vector should be zero
        assertEquals(0.0f, vectorA.dot(zeroVector), 0.001f);
    }

    @Test
    void testAngle() {
        Vector2 rightVector = new Vector2(1.0f, 0.0f);
        Vector2 upVector = new Vector2(0.0f, 1.0f);
        
        // Angle between right and up vectors should be 90 degrees (π/2 radians)
        float angle = rightVector.angle(upVector);
        assertEquals(Math.PI / 2, angle, 0.001f);
    }

    @Test
    void testFromAngle() {
        // 0 radians should give (1, 0)
        Vector2 fromZero = Vector2.fromAngle(0.0f);
        assertEquals(1.0f, fromZero.x, 0.001f);
        assertEquals(0.0f, fromZero.y, 0.001f);
        
        // π/2 radians should give (0, 1)
        Vector2 fromPiHalf = Vector2.fromAngle((float) (Math.PI / 2));
        assertEquals(0.0f, fromPiHalf.x, 0.001f);
        assertEquals(1.0f, fromPiHalf.y, 0.001f);
    }

    @Test
    void testClampLength() {
        Vector2 longVector = new Vector2(10.0f, 0.0f);
        Vector2 clamped = longVector.clampLength(5.0f);
        assertEquals(5.0f, clamped.length(), 0.001f);
        
        Vector2 shortVector = new Vector2(2.0f, 0.0f);
        Vector2 notClamped = shortVector.clampLength(5.0f);
        assertEquals(shortVector.x, notClamped.x);
        assertEquals(shortVector.y, notClamped.y);
    }

    @Test
    void testLerp() {
        Vector2 start = new Vector2(0.0f, 0.0f);
        Vector2 end = new Vector2(10.0f, 10.0f);
        
        Vector2 halfway = start.lerp(end, 0.5f);
        assertEquals(5.0f, halfway.x, 0.001f);
        assertEquals(5.0f, halfway.y, 0.001f);
        
        Vector2 quarter = start.lerp(end, 0.25f);
        assertEquals(2.5f, quarter.x, 0.001f);
        assertEquals(2.5f, quarter.y, 0.001f);
    }

    @Test
    void testRotateVector() {
        Vector2 rightVector = new Vector2(1.0f, 0.0f);
        
        // Rotate 90 degrees should give (0, 1)
        Vector2 rotated90 = rightVector.rotateVector(90.0f);
        assertEquals(0.0f, rotated90.x, 0.001f);
        assertEquals(1.0f, rotated90.y, 0.001f);
        
        // Rotate 180 degrees should give (-1, 0)
        Vector2 rotated180 = rightVector.rotateVector(180.0f);
        assertEquals(-1.0f, rotated180.x, 0.001f);
        assertEquals(0.0f, rotated180.y, 0.001f);
    }

    @Test
    void testSetMethods() {
        Vector2 testVector = new Vector2();
        
        // Test set with floats
        testVector.set(7.0f, 8.0f);
        assertEquals(7.0f, testVector.x);
        assertEquals(8.0f, testVector.y);
        
        // Test set with Vector2
        testVector.set(vectorA);
        assertEquals(vectorA.x, testVector.x);
        assertEquals(vectorA.y, testVector.y);
    }

    @Test
    void testCopy() {
        Vector2 copy = vectorA.copy();
        assertEquals(vectorA.x, copy.x);
        assertEquals(vectorA.y, copy.y);
        
        // Ensure it's a deep copy
        copy.x = 999.0f;
        assertNotEquals(vectorA.x, copy.x);
    }
} 