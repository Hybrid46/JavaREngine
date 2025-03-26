# R Engine - Java Game Engine

**R Engine** is a lightweight, component-based 2D game engine built in Java, designed for simplicity and flexibility. Developed by Hybrid, this engine provides essential tools and components to create 2D games with ease, featuring entity-component architecture, rendering, animation, collision detection, and input handling.

![Engine Architecture]((Workflow.png))
---

## Features

### Core Architecture
- **Entity-Component-System (ECS)**: Flexible architecture for game object composition
- **Game Loop**: Optimized loop with configurable FPS/UPS settings
- **Camera System**:
  - World-to-screen coordinate transformations
  - Zoom/rotation controls
  - Entity following functionality

### Rendering
- **Layered Rendering**: Z-index based rendering with `RenderUpdatable` interface
- **Sprite Animation**: `Animator` component for sprite sheet animations
- **Dynamic Transformations**: Rotation, scaling, and translation of entities

### Components
- **Transform**: Position, rotation, scale, and velocity management
- **Renderer**: Sprite rendering with bounding boxes
- **Colliders**:
  - `RectCollider` for AABB collisions
  - Collision resolution system
- **Parent-Child Hierarchy**: `Parent` component for transform inheritance

### Input Handling
- **Keyboard & Mouse Support**: Event-driven input system
- **Double-Click Detection**
- **Input State Tracking**: Pressed/released/held states

### Physics
- **Collision Detection**: Broad-phase and narrow-phase checks
- **Collision Resolution**: Impulse-based response system

### Utilities
- **Math Library**:
  - `Vector2/Vector2Int` for 2D math
  - `Matrix3x3` for transformations
- **Resource Management**: `ResourceLoader` for sprite loading
- **Screen Utilities**: Multi-monitor support and resolution handling

---

## Getting Started

### Prerequisites
- Java JDK 17+
- Maven

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Hybrid/JavaREngine.git
   ```

    Import into your IDE as a Maven project

Usage
Basic Entity Creation
```java
// Create a new entity
Entity player = new Entity(game, 0);

// Add components
Transform transform = player.getTransform();
transform.setPosition(new Vector2(100, 100));

Renderer renderer = new Renderer(transform, "player.png", 10);
player.addComponent(renderer);

RectCollider collider = new RectCollider(transform, new Vector2Int(32, 32));
player.addComponent(collider);
```

Animation System
```java
Animator animator = new Animator(
    transform, 
    renderer, 
    "spritesheet.png", 
    6
);
player.addComponent(animator);
```

Camera Control
```java
CameraManager camera = game.getCameraManager();
camera.setFollowEntity(player);
```

Input Handling
```java
// In update loop
if (game.keyboardInput.isKeyPressed(KeyEvent.VK_SPACE)) {
    // Jump logic
}

Vector2 mousePos = game.mouseInput.getMousePosition();
```

```mermaid
Component Documentation
Key Classes
Class	Description
Entity	Base game object container
Component	Base class for all components
Transform	Position/Rotation/Scale management
Renderer	Sprite rendering component
Camera	View/projection matrix management
Game Loop Structure
```

Example Project Structure
```
/src/main/java/
├── com/hybrid/rEngine/
│   ├── components/      # Core engine components
│   ├── math/            # Math utilities
│   ├── main/            # Game loop and managers
│   └── utils/           # Helper classes
└── com/hybrid/tankGame/ # Example game implementation
```

```
    A[Game Start] --> B[Initialize Entities]
    B --> C[Process Input]
    C --> D[Update Components]
    D --> E[Resolve Collisions]
    E --> F[Render Frame]
    F --> C
```

Examples
Tank Game Implementation

The engine includes a sample tank battle game demonstrating:

    Player/enemy controllers

    Projectile physics

    Level generation

    Save/load system

Key implementation files:

    Player.java - Player controller with movement/shooting

    LevelGenerator.java - Procedural map generation

    Bullet.java - Projectile physics implementation

Development Roadmap
Planned Features

    - Editor

    - Particle system

    - Audio system

    - Network component

    - Improved collision detection (SAT)

Contributing

Contributions are welcome!

License
This project is licensed under the MIT License - see LICENSE for details.

Created by Hybrid - Free for educational and commercial use.