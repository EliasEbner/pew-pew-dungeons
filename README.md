# pew-pew-dungeons

## Basic features:
- Dungeon
- Some enemies/traps/etc.
- Some weapons
- Player
- Possibility to exit dungeon

## Optional features
- More enemy types
- Procedurally generated dungeon floors
- More weapons
- Coins which you can spend in a shop
- Tables which can be flipped
- Chests / Loot
- Final boss


## Code structure

### interface Movable
- protected void move();

### interface Drawable
- public void draw();

### interface Updatable
- public void update();

### interface Collidable
- public void collidesWith(RigidBody body);

### interface AutoMovable
- public void autoMove();

### abstract class RigidBody implements Collidable
- protected Vector2 position;
- protected Vector2 size;

### abstract class GameObject implements Drawable, Updatable
- protected Vector2 position;
- protected Vector2 size;
- protected Vector2 orientation;

### abstract class Inventory
- protected List<Weapon> weapons;
- protected long coins;

### class Player extends GameObject implements Movable
- private double health;
- private double mana;
- private RigidBody rigidBody;
- private Inventory inventory;

### class Enemy extends GameObject implements AutoMovable
- private double health;
- private Inventory inventory;
- private RigidBody rigidBody;

### class Enemy1 extends Enemy
### class Enemy2 extends Enemy
...

### class Projectile extends GameObject implements AutoMovable
- private Vector2 direction;
- private RigidBody rigidBody;

### interface RangeWeapon
- shoot(Projectile projectile);

### abstract class Gun implements RangeWeapon
- private int (?)


### class Scene
- protected List<GameObject> gameObjects;
