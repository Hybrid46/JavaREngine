package com.hybrid.rEngine.components;

public abstract class Component {

    private boolean isActive = true;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
