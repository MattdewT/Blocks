package de.matze.Blocks.mechanics.terrain;

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector4f;

/**
 * @author matze tiroch
 * @version 1.0
 * Created by matze on 05.09.16.
 */

//ToDo: check class

public class TerrainShader extends Shader {

    private final static String vertPath = "src/de/matze/Blocks/mechanics/terrain/TerrainShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/terrain/TerrainShader.fs";

    private final static String vertPathB = "src/de/matze/Blocks/mechanics/terrain/TerrainShaderB.vs";
    private final static String fragPathB = "src/de/matze/Blocks/mechanics/terrain/TerrainShaderB.fs";

    private int Location_pr_matrix;
    private int Location_view_matrix;
    private int Location_ml_matrix;
    private int Location_center;
    private int Location_inner;
    private int Location_outer;
    private int Location_clipPlane;

    public TerrainShader(char shaderVersion) {
        super(vertPathB, fragPathB);
    }

    public TerrainShader() {
        super(vertPath, fragPath);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        Location_pr_matrix = getUniformLocation("pr_matrix");
        Location_view_matrix = getUniformLocation("view_matrix");
        Location_ml_matrix = getUniformLocation("ml_matrix");
        Location_center = getUniformLocation("center");
        Location_inner = getUniformLocation("inner");
        Location_outer = getUniformLocation("outer");
        Location_clipPlane = getUniformLocation("clipPlane");
    }

    public void setProjectionMatrix(Matrix4f pr_matrix) {
        super.setUniformMat4f(Location_pr_matrix, pr_matrix);
    }

    public void setViewMatrix(Matrix4f view_matrix) {
        super.setUniformMat4f(Location_view_matrix, view_matrix);
    }

    public void setModelMatrix(Matrix4f ml_matrix) {
        super.setUniformMat4f(Location_ml_matrix, ml_matrix);
    }

    public void setBlendValues(float center_x, float center_z, float inner, float outer) {
        super.setUniform2f(Location_center, center_x, center_z);
        super.setUniform1f(Location_inner, inner);
        super.setUniform1f(Location_outer, outer);
    }

    public void setClipPlane(Vector4f plane) {
        super.setUniform4f(Location_clipPlane, plane);
    }
}

