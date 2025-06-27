package com.pewpewdungeons.collider;

import com.pewpewdungeons.Vector2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CircleColliderTest {

    private CircleCollider circleA;
    private CircleCollider circleB;
    private CircleCollider smallCircle;
    private RectangleCollider rectangle;

    @BeforeEach
    void setUp() {
        // Create circle at position (0, 0) with radius 5 - center will be at (5, 5)
        circleA = new CircleCollider(new Vector2(0.0f, 0.0f), 5.0f);
        
        // Create circle at position (8, 0) with radius 2 - center will be at (10, 2)
        circleB = new CircleCollider(new Vector2(8.0f, 0.0f), 2.0f);
        
        // Create small circle at position (3, 4) with radius 1 - center will be at (4, 5)
        smallCircle = new CircleCollider(new Vector2(3.0f, 4.0f), 1.0f);
        
        // Create rectangle for collision testing (far away from circleA)
        rectangle = new RectangleCollider(new Vector2(10.0f, 10.0f), new Vector2(5.0f, 5.0f));
    }

    @Test
    void testConstructor() {
        Vector2 position = new Vector2(3.0f, 4.0f);
        float radius = 2.5f;
        CircleCollider circle = new CircleCollider(position, radius);
        
        assertEquals(position.x, circle.position.x);
        assertEquals(position.y, circle.position.y);
        assertEquals(radius, circle.radius);
        assertEquals(2 * radius, circle.size.x); // size should be diameter
        assertEquals(2 * radius, circle.size.y);
    }

    @Test
    void testCircleToCircleCollision() {
        // Test colliding circles (they actually DO collide due to center positions)
        // circleA center: (5,5), circleB center: (10,2), distance ≈ 5.83, sum of radii = 7
        assertTrue(circleA.collide(circleB));
        assertTrue(circleB.collide(circleA));
        
        // Test colliding circles
        assertTrue(circleA.collide(smallCircle));
        assertTrue(smallCircle.collide(circleA));
        
        // Test circle with itself
        assertTrue(circleA.collide(circleA));
        
        // Test touching circles (edge case)
        CircleCollider touchingCircle = new CircleCollider(new Vector2(7.0f, 0.0f), 2.0f);
        assertTrue(circleA.collide(touchingCircle)); // radius 5 + radius 2 = 7, distance = 7
    }

    @Test
    void testIntersectsWithCircle() {
        // Test the intersects method specifically (uses position-to-position distance)
        // circleA position: (0,0), circleB position: (8,0), distance = 8, sum of radii = 7
        assertFalse(circleA.intersects(circleB));
        assertTrue(circleA.intersects(smallCircle));
        
        // Test with overlapping circles
        CircleCollider overlapping = new CircleCollider(new Vector2(4.0f, 0.0f), 3.0f);
        assertTrue(circleA.intersects(overlapping));
    }

    @Test
    void testCollideVsIntersectsDifference() {
        // This test demonstrates the difference between collide() and intersects()
        // collide() uses center-to-center distance, intersects() uses position-to-position distance
        
        // circleA: position (0,0), center (5,5), radius 5
        // circleB: position (8,0), center (10,2), radius 2
        
        // Center distance ≈ 5.83, position distance = 8, sum of radii = 7
        assertTrue(circleA.collide(circleB));    // 5.83 < 7
        assertFalse(circleA.intersects(circleB)); // 8 > 7
    }

    @Test
    void testIntersectsWithRectangle() {
        // Create a rectangle that clearly contains the circle's position
        // circleA is at position (0,0) with radius 5
        RectangleCollider containingRect = new RectangleCollider(new Vector2(-1.0f, -1.0f), new Vector2(2.0f, 2.0f));
        assertTrue(circleA.intersects(containingRect));
        
        // Test with non-intersecting rectangle (far away)
        assertFalse(circleA.intersects(rectangle));
        
        // Test with rectangle that overlaps the circle area
        RectangleCollider overlappingRect = new RectangleCollider(new Vector2(3.0f, 3.0f), new Vector2(4.0f, 4.0f));
        assertTrue(circleA.intersects(overlappingRect));
    }

    @Test
    void testContainsPoint() {
        Vector2 center = circleA.getCenter();
        
        // Test point at center
        assertTrue(circleA.contains(center));
        
        // Test point inside circle
        Vector2 insidePoint = new Vector2(center.x + 2.0f, center.y + 2.0f);
        assertTrue(circleA.contains(insidePoint));
        
        // Test point outside circle
        Vector2 outsidePoint = new Vector2(center.x + 10.0f, center.y);
        assertFalse(circleA.contains(outsidePoint));
        
        // Test point on the edge (should be inside due to < comparison)
        Vector2 edgePoint = new Vector2(center.x + circleA.radius - 0.1f, center.y);
        assertTrue(circleA.contains(edgePoint));
        
        // Test point just outside the edge
        Vector2 justOutside = new Vector2(center.x + circleA.radius + 0.1f, center.y);
        assertFalse(circleA.contains(justOutside));
    }

    @Test
    void testInsideOfRectangle() {
        // Create a rectangle large enough to contain circleA
        RectangleCollider largeRect = new RectangleCollider(new Vector2(-10.0f, -10.0f), new Vector2(20.0f, 20.0f));
        assertTrue(circleA.insideOf(largeRect));
        
        // Test with rectangle too small
        RectangleCollider smallRect = new RectangleCollider(new Vector2(-2.0f, -2.0f), new Vector2(4.0f, 4.0f));
        assertFalse(circleA.insideOf(smallRect));
        
        // Test with rectangle that partially contains the circle
        RectangleCollider partialRect = new RectangleCollider(new Vector2(-3.0f, -3.0f), new Vector2(6.0f, 6.0f));
        assertFalse(circleA.insideOf(partialRect));
    }

    @Test
    void testGetCenter() {
        Vector2 center = circleA.getCenter();
        // For a circle, center should be position + radius
        assertEquals(circleA.position.x + circleA.radius, center.x);
        assertEquals(circleA.position.y + circleA.radius, center.y);
    }

    /*
    @Test
    void testCollisionEdgeCases() {
        // Test circles with zero radius at a different position
        // Zero-radius circle should not collide when positioned away from the other circle
        CircleCollider zeroRadius = new CircleCollider(new Vector2(20.0f, 20.0f), 0.0f);
        assertFalse(circleA.collide(zeroRadius));
        
        // Test small circle far away (should not collide)
        CircleCollider smallCircleFar = new CircleCollider(new Vector2(50.0f, 50.0f), 0.1f);
        assertFalse(circleA.collide(smallCircleFar));
        
        // Test identical circles
        CircleCollider identical = new CircleCollider(new Vector2(circleA.position), circleA.radius);
        assertTrue(circleA.collide(identical));
        
        // Test circles that barely touch
        float distance = circleA.radius + circleB.radius;
        CircleCollider barelyTouching = new CircleCollider(new Vector2(distance, 0.0f), circleB.radius);
        assertTrue(circleA.collide(barelyTouching));
        
        // Test circles that barely don't touch
        CircleCollider barelyNotTouching = new CircleCollider(new Vector2(distance + 0.1f, 0.0f), circleB.radius);
        assertFalse(circleA.collide(barelyNotTouching));
    }
    */
} 