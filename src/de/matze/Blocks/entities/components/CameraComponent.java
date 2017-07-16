package de.matze.Blocks.entities.components;

import de.matze.Blocks.entities.GameObject;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;

/**
 * Created by matze on 31.08.16.
 */

//ToDo: copied class

public class CameraComponent extends Component {

    public TransformComponent transformComponent;
    public ViewComponent viewComponent;

    public CameraComponent(GameObject g) {
        super(ComponentTypes.Camera);
        viewComponent = (ViewComponent) g.getComponent(ComponentTypes.ViewPoint);
        transformComponent = (TransformComponent) g.getComponent(ComponentTypes.Transform);
    }

    public Vector3f getViewPoint() {
        return viewComponent.viewpoint;
    }

    public Vector3f getPosition() {
        return transformComponent.getPos();
    }

    public Matrix4f getViewMatrix() {
        return Matrix4f.ViewMatrix(getViewPoint(), getPosition());
    }

    public void invertPitch() {
        viewComponent.viewpoint.x = -viewComponent.viewpoint.x;
    }
}
