package de.matze.Blocks.entities.components;

import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;

/**
 * Created by matze on 29.08.16.
 */

//ToDo: copied class

public class TransformComponent extends Component{

    private Vector3f pos;
    private Vector3f rot;

    public TransformComponent(Vector3f pos) {
        super(ComponentTypes.Transform);
        this.pos = pos;
    }

    //ToDo: Implementation of rotation
    public Matrix4f getModelMatrix() {
        return Matrix4f.translate(pos);
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getRot() {
        return rot;
    }

    public void setRot(Vector3f rot) {
        this.rot = rot;
    }
}
