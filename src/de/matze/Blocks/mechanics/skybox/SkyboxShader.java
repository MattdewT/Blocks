package de.matze.Blocks.mechanics.skybox;

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;

//ToDo: copied class

/**
 *
 */

public class SkyboxShader extends Shader {

    private static final String Vertex_Shader_Location = "src/de/matze/Blocks/mechanics/skybox/skybox.vs";
    private static final String Fragment_Shader_Location = "src/de/matze/Blocks/mechanics/skybox/skybox.fs";

    private int Location_pr_matrix;
    private int Location_view_matrix;

    public SkyboxShader() {
        super(Vertex_Shader_Location, Fragment_Shader_Location);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

    @Override
    protected void getAllUniformLocations() {
        Location_pr_matrix = getUniformLocation("pr_matrix");
        Location_view_matrix = getUniformLocation("view_matrix");
    }

    public void setProjectionMatrix(Matrix4f pr_matrix) {
        super.setUniformMat4f(Location_pr_matrix, pr_matrix);
    }

    public void setViewMatrix(Matrix4f view_matrix) {
        super.setUniformMat4f(Location_view_matrix, view_matrix);
    }

}
