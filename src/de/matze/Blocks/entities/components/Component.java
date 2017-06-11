package de.matze.Blocks.entities.components;

/**
 * Created by matze on 26.08.16.
 */

//ToDo: copied class

public class  Component {

    private boolean isEnable;
    private ComponentTypes ComponentType;

    public enum ComponentTypes {
        Transform, ViewPoint, Camera, Player;
    }

    public Component(ComponentTypes ComponentId) {
        this.ComponentType = ComponentId;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public ComponentTypes getComponentType() {
        return ComponentType;
    }
}
