package com.hybrid.rEngine.components;

import com.hybrid.rEngine.main.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {

    private final int owner;
    private final Game game;
    private final Transform my_transform;
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    private boolean isStatic = false;
    private boolean destroy = false;

    public Entity(Game game, int owner) {
        this.game = game;
        this.owner = owner;
        my_transform = new Transform(this);
        addComponent(my_transform);
        game.registerEntity(this);
    }

    //Handle components by type
    public <T extends Component> T removeComponent(Class<T> componentType) {
        if (destroy) {
            System.err.println("Cannot remove component from destroyed entity!");
            return null;
        }

        if (!hasComponent(componentType)) {
            return null;
        }

        T component = componentType.cast(components.remove(componentType));

        return component;
    }

    public <T extends Component> T getComponent(Class<T> componentType) {
        if (!hasComponent(componentType)) {
            System.err.println("Component(" + componentType.toString() + ") not exists!");
            return null;
        }

        return componentType.cast(components.get(componentType));
    }

    public <T extends Component> boolean hasComponent(Class<T> componentType) {
        return components.containsKey(componentType);
    }

    //Handle components by reference
    public void addComponent(Component component) {
        if (component == null) {
            System.err.println("Component is null!");
            return;
        }

        if (destroy) {
            System.err.println("Cannot add component to destroyed entity!");
            return;
        }

        Component existing = components.get(component.getClass());
        if (existing != null) {
            removeComponent(existing.getClass());
        }

        components.put(component.getClass(), component);
    }

    public boolean containsComponent(Component component) {
        return components.containsValue(component);
    }

    public boolean removeComponent(Component component) {
        return (removeComponent(component.getClass()) != null);
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public ArrayList<Updatable> getUpdatables() {
        ArrayList<Updatable> updatables = new ArrayList<>();

        if (this instanceof Updatable updatable) {
            updatables.add(updatable);
        }

        for (Map.Entry<Class<? extends Component>, Component> entry : components.entrySet()) {
            Class<? extends Component> componentClass = entry.getKey();
            Component componentInstance = entry.getValue();

            if (componentInstance instanceof Updatable updatable) {
                updatables.add(updatable);
            }

            //System.out.println("Class: " + componentClass.getName());
            //System.out.println("Component: " + componentInstance);
        }

        return updatables;
    }

    public ArrayList<RenderUpdatable> getRenderUpdatables() {
        ArrayList<RenderUpdatable> renderUpdatables = new ArrayList<>();

        if (this instanceof RenderUpdatable renderUpdatable) {
            renderUpdatables.add(renderUpdatable);
        }

        for (Map.Entry<Class<? extends Component>, Component> entry : components.entrySet()) {
            Class<? extends Component> componentClass = entry.getKey();
            Component componentInstance = entry.getValue();

            if (componentInstance instanceof RenderUpdatable renderUpdatable) {
                renderUpdatables.add(renderUpdatable);
            }

            //System.out.println("Class: " + componentClass.getName());
            //System.out.println("Component: " + componentInstance);
        }

        return renderUpdatables;
    }

    public Transform getTransform() {
        return my_transform;
    }

    public Game getGame() {
        return game;
    }

    public int getOwner(){
        return owner;
    }

    public boolean markedToDestroy(){
        return destroy;
    }

    public void Destroy() {
        game.unregisterEntity(this);
        destroy = true;
    }
}
