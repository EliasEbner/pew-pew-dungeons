# Pew-Pew-Dungeons

A 2D dungeon crawler game built in Java with procedurally generated dungeons, multiple enemy types, and various weapons.

## Team Members

- **Member 1**: Tobias - GitHub: [@optobimus](https://github.com/optobimus)
- **Member 2**: Elias - GitHub: [@EliasEbner](https://github.com/EliasEbner)
- **Member 3**: Nicholas - GitHub: [@hgoschuetz](https://github.com/hgoschuetz)
- **Member 4**: Tomi - GitHub: [@Tomsbax](https://github.com/Tomsbax)

## Project Description

Pew-Pew-Dungeons is a top-down 2D action game where players navigate through procedurally generated dungeons, fight various enemies, and collect weapons and coins. The game features real-time combat with both melee and ranged weapons, dynamic camera following, and a collision detection system.

### Key Features

- **Procedurally Generated Dungeons**: Each playthrough offers a unique dungeon layout with connected rooms
- **Multiple Enemy Types**:
    - Tank enemies with high health
    - Runner enemies with fast movement
    - Ranged enemies that attack from distance
- **Weapon System**: Various weapons including guns (burst, shotgun) and melee weapons (sword)
- **Inventory System**: Manage weapons and coins collected throughout the game
- **Real-time Combat**: Dynamic projectile system with collision detection
- **Responsive Controls**: Smooth player movement and camera following

## Building and Running the Project

### Prerequisites

- **Java 21** or higher
- **Maven 3.6+**
- **Git** (for cloning the repository)

### Build Instructions

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd pew-pew-dungeons
   ```

2. **Build the project**:
   ```bash
   mvn clean compile
   ```

3. **Run tests**:
   ```bash
   mvn test
   ```

4. **Run the game**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.pewpewdungeons.Main"
   ```

   Or with custom screen resolution:
   ```bash
   mvn exec:java -Dexec.mainClass="com.pewpewdungeons.Main" -Dexec.args="-screenWidth 1920 -screenHeight 1080"
   ```

### Alternative: Create JAR file

```bash
mvn package
java -jar target/pew-pew-dungeons-1.0-SNAPSHOT.jar
```

## User Guide

### Controls

- **Movement**: WASD keys
- **Attack**: Left mouse button
- **Weapon Selection**: Number keys (1, 2, 3)
- **Inventory**: E key (toggle)
- **Restart Game**: R key
- **Interaction**: T key

### Gameplay

1. **Start**: The game begins in a procedurally generated dungeon
2. **Movement**: Use WASD to navigate through rooms
3. **Combat**: Left-click to attack enemies with your current weapon
4. **Weapons**: Switch between different weapons using number keys
5. **Inventory**: Press E to view your weapons and coin count
6. **Exploration**: Move between rooms through doors
7. **Restart**: Press R to generate a new dungeon and restart

### Objective

Survive as long as possible while exploring the dungeon, defeating enemies, and collecting weapons and coins.

## Implementation Overview

### High-Level Architecture

The project follows an object-oriented design with clear separation of concerns:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Game Loop     │────│   World         │────│   Entities      │
│   (Main.java)   │    │   (Dungeon)     │    │   (Player,      │
│                 │    │                 │    │    Enemies)     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │              ┌─────────────────┐              │
         │              │   Systems       │              │
         └──────────────│   (Projectile,  │──────────────┘
                        │    Input,       │
                        │    Collision)   │
                        └─────────────────┘
```

### Core Components

#### 1. **Entity System** (`entities/`)
- **GameObject**: Base class for all game objects (implements `Drawable`, `Updatable`)
- **Player**: Main character with health, inventory, and movement
- **Enemy**: Base enemy class with AI behavior (Tank, Runner, Ranged variants)
- **Interfaces**: `Drawable`, `Updatable`, `AutoMovable` for behavior contracts

#### 2. **World Management** (`world/`)
- **Dungeon**: Main world container managing rooms and entities
- **DungeonGenerator**: Procedural generation of dungeon layouts
- **Room**: Individual rooms with doors and entity management
- **Door**: Connections between rooms with orientation handling

#### 3. **Combat System** (`projectiles/`, `items/`)
- **ProjectileSystem**: Global management of all projectiles
- **Weapon**: Abstract base for all weapons (Gun, Sword, etc.)
- **Projectile**: Base projectile with collision and movement

#### 4. **Collision Detection** (`collider/`)
- **Collider**: Abstract collision interface
- **CircleCollider** & **RectangleCollider**: Specific collision shapes
- **NullCollider**: No-collision implementation

#### 5. **Support Systems**
- **InputManager**: Centralized input handling for keyboard and mouse
- **Camera**: 2D camera system with following behavior
- **GameLogger**: Structured logging with performance metrics
- **Vector2**: 2D vector mathematics utility

### Third-Party Libraries

1. **Raylib (via Jaylib 5.0.0)**:
    - 2D graphics rendering
    - Input handling
    - Window management
    - Cross-platform compatibility

2. **JUnit Jupiter 5.10.0**:
    - Unit testing framework
    - Assertion utilities

3. **SLF4J + Logback**:
    - Structured logging
    - Performance monitoring
    - Configurable log levels

### Programming Techniques

#### Design Patterns Used
- **Component Pattern**: GameObject with drawable/updatable components
- **Strategy Pattern**: Different AI behaviors for enemy types
- **Singleton Pattern**: InputManager for global input access
- **Factory Pattern**: DungeonGenerator for creating dungeon layouts

#### Key Programming Concepts
- **Inheritance**: GameObject hierarchy for entities
- **Polymorphism**: Different enemy behaviors through inheritance
- **Encapsulation**: Private fields with controlled access
- **Interface Segregation**: Separate interfaces for different behaviors
- **Resource Management**: Proper cleanup of native Raylib resources
- **Event-Driven Architecture**: Input handling and game events

## Human Experience

### Workload Distribution

- **Member 1**: Tobias - Responsibilities:
    - Project Setup with Maven, integrating Jaylib/Raylib dependencies
    - Main Game Loop: Integrated main systems basic layout in the beginning
    - Performance monitoring: Built comprehensive logging and performance tracking
    - Procuderal generation: Implemented DungeonGenerator with room placement algorithms
    - Weapon Switching with keys 1,2,3
    - Created base enemy system and multiple enemy types (Standard, Tank, fast,..) with spawning algorithm
    - Enemy-Player Interaction: Implemented damage dealing and health systems
    - Logging Infrastructure: Created centralized logging system (separate loggers for different systems)
- **Member 2**: Elias - Responsibilities:
- **Member 3**: Nicholas - Responsibilities:
- **Member 4**: Tomi - Responsibilities:
    - Weapons: Different weapon types from ranged to meele
    - Teleportation: Teleportation ability with the usage of mana
    - Mana-System: Manabar as UI Feature and Mana regeneration
    - Health-System: Regenerate health on enemy kills
    - Debug: Debug output as a UI feature
    - Bugfix: Edge case interactions between colliders

### Git Usage

- **Branching Strategy**: Mainly synchronous workflow (since everybody has their preferred developing times), mainly commits on master branch
- **Issue Tracking**: Implemented centralized logging systems as well as JUnit tests for most important central parts of the code

### Challenges Faced

#### Challenges - Tobias
- Native Library Integration Challenge - Integrating Raylib through Jaylib
- Prodecural Generation Algorithm  - Creating connected dungeon layouts without overlapping rooms
- Performance & Debugging - Comprehensive logging system with multiple log levels
- System integration challenge - Coordinating multiple complex systems an package structure

#### Challenges - Elias
- Created initial prototype of dungeon generation algorithm and room connections
- Implemented sliding doors which separate the rooms
- Initial boilerplate (player movement and other basic things)
- Helped work on the gun's rotation (gun points to the mouse cursor)
- Collisions with rooms / dungeons
- Main challenge: optimizing the collision algorithm (don't check all rooms, only check rooms near the player)

#### Challenges - Nicholas
- [Specific challenge and how it was overcome]

#### Challenges - Tomi
- Inconsistent colliders - Checking edge cases when working with collisions
- Teleporting always to the same location - Randomizing directions and implementing a predefined distance
- Teleporting causing issues with collider - Refresh collider position

### Technical Challenges Overcome
- **Native Library Integration**: Successfully integrated Raylib through Jaylib bindings
- **Memory Management**: Proper cleanup of native resources to prevent memory leaks
- **Collision Detection**: Implemented efficient collision system for multiple object types
- **Procedural Generation**: Created algorithm for generating connected dungeon layouts
- **Performance Optimization**: Implemented performance monitoring and optimization techniques

## Development Notes

### Logging

The project includes comprehensive logging through the `GameLogger` class. Logs are stored in the `logs/` directory with different levels (INFO, WARN, ERROR). Performance metrics and game events are tracked for debugging and optimization.

### Testing

Unit tests are provided for key components:
- `PlayerInventoryTest`: Tests inventory functionality
- `Vector2Test`: Mathematical operations testing
- `CircleColliderTest`: Collision detection testing
- `UtilTest`: Utility function testing

### Configuration

- **Window Resolution**: Configurable via command line arguments
- **FPS Target**: Set to 60 FPS
- **Logging Configuration**: Managed through `logback.xml`

## License

This project is licensed under the terms specified in the LICENSE file.
