package de.matze.Blocks.entities.components;

import de.matze.Blocks.maths.Vector3f;

/**
 * Provides an Entity with an Viewpoint (e.g. Camera, Viewpoint for enemies which dont need a camera component)
 * Created by matze on 31.08.16.
 */

//ToDo: copied class | redo Class description

public class ViewComponent extends Component {

    public Vector3f viewpoint;

    public ViewComponent() {
        super(ComponentTypes.ViewPoint);
        viewpoint = new Vector3f();
    }

    public void invertPitch() {
        viewpoint.x = -viewpoint.x;
    }
}
