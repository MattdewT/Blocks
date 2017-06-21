package de.matze.Blocks.mechanics.terrain;

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;

/**
 * @author matze tiroch
 * @version 1.0
 * Created by matze on 05.09.16.
 */

//ToDo: check class

public class TerrainShader extends Shader {

    private final static String vertPath = "src/de/matze/Blocks/mechanics/terrain/TerrainShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/terrain/TerrainShader.fs";

    private int Location_pr_matrix;
    private int Location_view_matrix;
    private int Location_ml_matrix;

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
}

