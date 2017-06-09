package de.matze.Blocks.entities.components;

import de.matze.Blocks.entities.GameObject;
import de.matze.Blocks.input.Keyboard;
import de.matze.Blocks.input.MousePos;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.utils.WindowUtils;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by matze on 31.08.16.
 */

//ToDo: copied class

public class PlayerComponent extends Component {

    private static final float RUN_SPEED = 100;

    private float currentSpeedX = 0;
    private float currentSpeedZ = 0;
    private float currentSpeedY = 0;

    private float rotY;
    private float rotX;

    private CameraComponent cameraComponent;

    public PlayerComponent(GameObject g) {
        super(ComponentTypes.Player);
        cameraComponent = (CameraComponent) g.getComponent(ComponentTypes.Camera);
    }

    public Matrix4f getViewMatrix() {
        return cameraComponent.getViewMatrix();
    }

    public void Update() {
        checkInputs();

        float distanceX = currentSpeedX * WindowUtils.getFrameTimeSeconds();
        float distanceZ = currentSpeedZ * WindowUtils.getFrameTimeSeconds();

        float dx = (float) (distanceX * Math.cos(Math.toRadians(rotY + 90)));
        float dz = (float) (distanceX * Math.sin(Math.toRadians(rotY + 90)));

        dx += (float) (distanceZ * Math.cos(Math.toRadians(rotY)));
        dz += (float) (distanceZ * Math.sin(Math.toRadians(rotY)));

        float dy = currentSpeedY * WindowUtils.getFrameTimeSeconds();

        cameraComponent.transformComponent.pos.x -= dx;
        cameraComponent.transformComponent.pos.z -= dz;
        cameraComponent.transformComponent.pos.y -= dy;

        cameraComponent.viewComponent.viewpoint.x = rotX;
        cameraComponent.viewComponent.viewpoint.y = rotY;
    }


    private void checkInputs() {
        rotY += 0.2f * (float) MousePos.getMouseDX();
        rotX += 0.2f * (float) MousePos.getMouseDY();

        if (Keyboard.keys[GLFW_KEY_W]) {
            currentSpeedX = -RUN_SPEED;
        } else if (Keyboard.keys[GLFW_KEY_S]) {
            currentSpeedX = RUN_SPEED;
        } else {
            currentSpeedX = 0;
        }
        if (Keyboard.keys[GLFW_KEY_A]) {
            currentSpeedZ = -RUN_SPEED;
        } else if (Keyboard.keys[GLFW_KEY_D]) {
            currentSpeedZ = RUN_SPEED;
        } else {
            currentSpeedZ = 0;
        }
        if (Keyboard.keys[GLFW_KEY_LEFT_SHIFT]) {
            currentSpeedY = -RUN_SPEED;
        } else if (Keyboard.keys[GLFW_KEY_SPACE]) {
            currentSpeedY = RUN_SPEED;
        } else {
            currentSpeedY = 0;
        }
        MousePos.setMouseDX(0);
        MousePos.setMouseDY(0);
    }
}
