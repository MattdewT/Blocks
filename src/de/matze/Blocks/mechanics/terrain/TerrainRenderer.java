package de.matze.Blocks.mechanics.terrain;

import de.matze.Blocks.maths.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * @author matze tiroch
 * @version 1.0
 * Created by matze on 05.09.16.
 */

//ToDo: check class

public class TerrainRenderer {

    private TerrainShader shader;

    public TerrainRenderer(Matrix4f pr_matrix, TerrainShader shader) {
        this.shader = shader;
        shader.enable();
        shader.setProjectionMatrix(pr_matrix);
        shader.disable();
    }

    public void render(List<TerrainTile> terrains) {
        for(TerrainTile t : terrains) {
//			if(!frustum.inFrustum(t.getModel().getAABB().getCenter(), t.getModel().getAABB().getRadius()))
//				continue;

            prepareTerrain(t);
//            shader.setModelMatrix(t.getModelMatrix());//ToDo change back
            shader.setModelMatrix(Matrix4f.identity());
            glDrawElements(GL_TRIANGLES, t.getModel().getCount(), GL_UNSIGNED_INT, 0);
            unbind();
        }
    }

    public void prepareTerrain(TerrainTile t) {
        glBindVertexArray(t.getModel().getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
//        glEnableVertexAttribArray(2);
//        glBindTexture(GL_TEXTURE_2D, t.getTexture().getTexID());
    }

    public void unbind() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
//        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
