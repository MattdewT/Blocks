package de.matze.Blocks.mechanics.water;

//ToDo: Copied Class

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;

public class WaterShader extends Shader {

	private final static String VERTEX_FILE = "src/de/matze/Blocks/mechanics/water/WaterShader.vs";
	private final static String FRAGMENT_FILE = "src/de/matze/Blocks/mechanics/water/WaterShader.fs";

	private int Location_modelMatrix;
	private int Location_viewMatrix;
	private int Location_projectionMatrix;
	private int Location_reflectionTexture; 
	private int Location_refractionTexture; 
	private int Location_dudvMap; 
	private int Location_moveFactor;
	private int Location_cameraPos;
	private int Location_dethMap;
	private int Location_near;
	private int Location_far;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		Location_projectionMatrix = getUniformLocation("projectionMatrix");
		Location_viewMatrix = getUniformLocation("viewMatrix");
		Location_modelMatrix = getUniformLocation("modelMatrix");
		Location_reflectionTexture = getUniformLocation("reflectionTexture");
		Location_refractionTexture = getUniformLocation("refractionTexture");
		Location_dudvMap = getUniformLocation("dudvMap");
		Location_moveFactor = getUniformLocation("moveFactor");
		Location_cameraPos = getUniformLocation("cameraPos");
		Location_dethMap  = getUniformLocation("depthMap");
		Location_near = getUniformLocation("near");
		Location_far = getUniformLocation("far");
	}
	
	public void connectTextureUnits() {
		setUniform1i(Location_reflectionTexture, 0);
		setUniform1i(Location_refractionTexture, 1);
		setUniform1i(Location_dudvMap, 2);
		setUniform1i(Location_dethMap, 3);
	}
	
	public void setMoveFactor(float o) {
		setUniform1f(Location_moveFactor, o);
	}
	
	public void setCameraVector(Vector3f pos) {
		setUniform3f(Location_cameraPos, pos);
	}
	
	public void setNearFarPlaney(float near, float far) {
		setUniform1f(Location_near, near);
		setUniform1f(Location_far, far);
	}

	public void setProjectionMatrix(Matrix4f projection) {
		setUniformMat4f(Location_projectionMatrix, projection);
	}
	
	public void setViewMatrix(Matrix4f view_matrix){
		setUniformMat4f(Location_viewMatrix, view_matrix);
	}

	public void setModelMatrix(Matrix4f modelMatrix){
		setUniformMat4f(Location_modelMatrix, modelMatrix);
	}

}
