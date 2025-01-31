package com.hybrid.rEngine.components;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Entity {

    private boolean isStatic = false;
    private final Transform my_transform;
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();

    public Entity() {
        my_transform = new Transform();
        addComponent(my_transform);
    }

    //Handle components by type
    public <T extends Component> T removeComponent(Class<T> componentType) {
        if (!hasComponent(componentType)) {
            return null;
        }

        T component = componentType.cast(components.remove(componentType));

        if (component instanceof Updatable updatable) {
            //Game.instance().unregisterUpdatable(updatable);
        }
        if (component instanceof RenderUpdatable renderUpdatable) {
            //Game.instance().unregisterRenderUpdatable(renderUpdatable);
        }

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

        if (component instanceof Updatable updatable) {
            //Game.instance().registerUpdatable(updatable);
        }
        if (component instanceof RenderUpdatable renderUpdatable) {
            //Game.instance().registerRenderUpdatable(renderUpdatable);
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

    public List<Updatable> getUpdatables() {
        List<Updatable> updatables = new ArrayList<>();
        for (Component component : components.values()) {
            if (component instanceof Updatable updatable) {
                updatables.add(updatable);
            }
        }
        return updatables;
    }

    public List<RenderUpdatable> getRenderUpdatables() {
        List<RenderUpdatable> renderUpdatables = new ArrayList<>();
        for (Component component : components.values()) {
            if (component instanceof RenderUpdatable renderUpdatable) {
                renderUpdatables.add(renderUpdatable);
            }
        }
        return renderUpdatables;
    }
    
    public Transform transform(){
        return my_transform;
    }
}
