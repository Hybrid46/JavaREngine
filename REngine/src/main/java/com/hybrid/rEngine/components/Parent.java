package com.hybrid.rEngine.components;

import java.util.ArrayList;
import java.util.List;

public class Parent extends Component implements Updatable{

    private Transform parentTransform;
    private final List<Transform> children = new ArrayList<>();

    private Parent() { }

    public Parent(Transform parentTransform){
        this.parentTransform = parentTransform;
    }

    @Override
    public void update() {
        if (children.isEmpty()) return;
        for (Transform child : children) child.setPosition(parentTransform.getPosition());
    }

    public boolean addChild(Transform childTransform){
        boolean containsChild = children.contains(childTransform);

        if (!containsChild) children.add(childTransform);

        return containsChild;
    }

    public boolean removeChild(Transform childTransform){
        boolean containsChild = children.contains(childTransform);

        if (containsChild) children.remove(childTransform);

        return containsChild;
    }

    public int getChildCount (){
        return children.size();
    }
}
