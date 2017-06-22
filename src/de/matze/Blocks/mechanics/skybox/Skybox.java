package de.matze.Blocks.mechanics.skybox;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

//ToDo: copied class

public class Skybox {


    private static final float SIZE = 500f;

    private static final float[] vertices = {
            -SIZE, SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, SIZE,
            -SIZE, -SIZE, SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, SIZE,

            -SIZE, SIZE, -SIZE,
            SIZE, SIZE, -SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            -SIZE, SIZE, SIZE,
            -SIZE, SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE, SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE, SIZE,
            SIZE, -SIZE, SIZE
    };

    private static String[] TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"};

    private SkyboxShader skyboxshader;
    private int CubeMap;
    private VertexArray CubeMesh;

    public Skybox(Loader loader, Matrix4f ProjectionMatrix) {
        CubeMap = loader.loadCubeMap(TEXTURE_FILES);
        CubeMesh = loader.loadToVAO(vertices, 3);

        skyboxshader = new SkyboxShader();
        skyboxshader.enable();
        skyboxshader.setProjectionMatrix(ProjectionMatrix);
        skyboxshader.disable();
    }

    public void render(Vector3f cam_viewpoint) {
        skyboxshader.enable();
        skyboxshader.setViewMatrix(Matrix4f.ViewMatrix(cam_viewpoint, new Vector3f()));
        glBindVertexArray(CubeMesh.getVaoID());
        glEnableVertexAttribArray(0);
        GL13.glActiveTexture(CubeMap);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, CubeMap);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, CubeMesh.getCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        skyboxshader.disable();
    }

}
