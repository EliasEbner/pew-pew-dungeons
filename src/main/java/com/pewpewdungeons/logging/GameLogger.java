package com.pewpewdungeons.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Centralized logging utility for our Pew-Pew-Dungeons game.
 * Provides convenient methods for logging gameevents, performance metrics,
 * and other application-specific information.
 */
public class GameLogger {
    
    // Core application loggers
    private static final Logger MAIN_LOGGER = LoggerFactory.getLogger("com.pewpewdungeons.main");
    private static final Logger GAME_EVENTS_LOGGER = LoggerFactory.getLogger("com.pewpewdungeons.events");
    private static final Logger PERFORMANCE_LOGGER = LoggerFactory.getLogger("com.pewpewdungeons.performance");
    
    // Component-specific loggers
    private static final Logger WORLD_LOGGER = LoggerFactory.getLogger("com.pewpewdungeons.world");
    private static final Logger ENTITIES_LOGGER = LoggerFactory.getLogger("com.pewpewdungeons.entities");
    private static final Logger PROJECTILES_LOGGER = LoggerFactory.getLogger("com.pewpewdungeons.projectiles");
    private static final Logger AI_LOGGER = LoggerFactory.getLogger("com.pewpewdungeons.ai");
    private static final Logger INPUT_LOGGER = LoggerFactory.getLogger("com.pewpewdungeons.input");
    
    private GameLogger() {
    }
    
    // === Main Application Logging ===
    
    public static void info(String message) {
        MAIN_LOGGER.info(message);
    }
    
    public static void info(String message, Object... args) {
        MAIN_LOGGER.info(message, args);
    }
    
    public static void debug(String message) {
        MAIN_LOGGER.debug(message);
    }
    
    public static void debug(String message, Object... args) {
        MAIN_LOGGER.debug(message, args);
    }
    
    public static void warn(String message) {
        MAIN_LOGGER.warn(message);
    }
    
    public static void warn(String message, Object... args) {
        MAIN_LOGGER.warn(message, args);
    }
    
    public static void error(String message) {
        MAIN_LOGGER.error(message);
    }
    
    public static void error(String message, Throwable throwable) {
        MAIN_LOGGER.error(message, throwable);
    }
    
    public static void error(String message, Object... args) {
        MAIN_LOGGER.error(message, args);
    }
    
    // === Game Events Logging ===
    
    public static void logGameEvent(String event) {
        GAME_EVENTS_LOGGER.info(event);
    }
    
    public static void logGameEvent(String event, Object... args) {
        GAME_EVENTS_LOGGER.info(event, args);
    }
    
    public static void logPlayerAction(String action) {
        GAME_EVENTS_LOGGER.info("PLAYER: {}", action);
    }
    
    public static void logEnemyAction(String enemyType, String action) {
        GAME_EVENTS_LOGGER.info("ENEMY[{}]: {}", enemyType, action);
    }
    
    public static void logWeaponEvent(String weaponType, String event) {
        GAME_EVENTS_LOGGER.info("WEAPON[{}]: {}", weaponType, event);
    }
    
    public static void logProjectileEvent(String projectileType, String event) {
        GAME_EVENTS_LOGGER.info("PROJECTILE[{}]: {}", projectileType, event);
    }
    
    public static void logWorldEvent(String event) {
        GAME_EVENTS_LOGGER.info("WORLD: {}", event);
    }
    
    // === Performance Logging ===
    
    public static void logPerformance(String metric, double value) {
        PERFORMANCE_LOGGER.info("{}: {}", metric, value);
    }
    
    public static void logFrameTime(double frameTime) {
        PERFORMANCE_LOGGER.debug("FRAME_TIME: {:.3f}ms", frameTime * 1000);
    }
    
    public static void logEntityCount(String entityType, int count) {
        PERFORMANCE_LOGGER.debug("ENTITY_COUNT[{}]: {}", entityType, count);
    }
    
    public static void startPerformanceTimer(String operation) {
        long startTime = System.nanoTime();
        MDC.put(operation + "_start", String.valueOf(startTime));
    }
    
    public static void endPerformanceTimer(String operation) {
        String startTimeStr = MDC.get(operation + "_start");
        if (startTimeStr != null) {
            long startTime = Long.parseLong(startTimeStr);
            long endTime = System.nanoTime();
            double durationMs = (endTime - startTime) / 1_000_000.0;
            PERFORMANCE_LOGGER.info("TIMER[{}]: {:.3f}ms", operation, durationMs);
            MDC.remove(operation + "_start");
        }
    }
    
    // === Component-Specific Logging ===
    
    public static class World {
        public static void info(String message, Object... args) {
            WORLD_LOGGER.info(message, args);
        }
        
        public static void debug(String message, Object... args) {
            WORLD_LOGGER.debug(message, args);
        }
        
        public static void warn(String message, Object... args) {
            WORLD_LOGGER.warn(message, args);
        }
        
        public static void error(String message, Object... args) {
            WORLD_LOGGER.error(message, args);
        }
    }
    
    public static class Entities {
        public static void info(String message, Object... args) {
            ENTITIES_LOGGER.info(message, args);
        }
        
        public static void debug(String message, Object... args) {
            ENTITIES_LOGGER.debug(message, args);
        }
        
        public static void warn(String message, Object... args) {
            ENTITIES_LOGGER.warn(message, args);
        }
        
        public static void error(String message, Object... args) {
            ENTITIES_LOGGER.error(message, args);
        }
    }
    
    public static class Projectiles {
        public static void info(String message, Object... args) {
            PROJECTILES_LOGGER.info(message, args);
        }
        
        public static void debug(String message, Object... args) {
            PROJECTILES_LOGGER.debug(message, args);
        }
        
        public static void warn(String message, Object... args) {
            PROJECTILES_LOGGER.warn(message, args);
        }
        
        public static void error(String message, Object... args) {
            PROJECTILES_LOGGER.error(message, args);
        }
    }
    
    public static class AI {
        public static void info(String message, Object... args) {
            AI_LOGGER.info(message, args);
        }
        
        public static void debug(String message, Object... args) {
            AI_LOGGER.debug(message, args);
        }
        
        public static void warn(String message, Object... args) {
            AI_LOGGER.warn(message, args);
        }
        
        public static void error(String message, Object... args) {
            AI_LOGGER.error(message, args);
        }
    }
    
    public static class Input {
        public static void info(String message, Object... args) {
            INPUT_LOGGER.info(message, args);
        }
        
        public static void debug(String message, Object... args) {
            INPUT_LOGGER.debug(message, args);
        }
        
        public static void warn(String message, Object... args) {
            INPUT_LOGGER.warn(message, args);
        }
        
        public static void error(String message, Object... args) {
            INPUT_LOGGER.error(message, args);
        }
    }
} 