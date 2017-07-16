package de.matze.Blocks.mechanics.water;

import de.matze.Blocks.entities.components.CameraComponent;
import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.utils.WindowUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

//ToDo: Copied Class

public class WaterRenderer {

	private final String DUDV_MAP = "res/textures/waterDUDV.jpg";
	private int dudvTexture;
	
	private VertexArray quad;
	private WaterShader shader;
	private WaterFrameBuffers fbos;
	
	private final float WaveSpeed = 0.03f;
	private float moveFactor = 0;

	public WaterRenderer(Loader loader, WaterShader shader, Matrix4f projectionMatrix, WaterFrameBuffers fbos, float near, float far) {
		this.shader = shader;
		this.fbos = fbos;
		dudvTexture = loader.loadTexture(DUDV_MAP);
		shader.enable();
		shader.setNearFarPlaney(near, far);
		shader.setProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.disable();
		setUpVAO(loader);
	}

	public void render(List<WaterTile> water, CameraComponent camera) {
		prepareRender(camera);	
		for (WaterTile tile : water) {
			Matrix4f modelMatrix = Matrix4f.multiply(Matrix4f.translate(new Vector3f(tile.getX(), tile.getHeight(), tile.getZ())), Matrix4f.scale(WaterTile.TILE_SIZE, WaterTile.TILE_SIZE, WaterTile.TILE_SIZE));
			shader.setModelMatrix(modelMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getCount());
		}
		unbind();
	}
	
	private void prepareRender(CameraComponent camera){
		moveFactor += WaveSpeed * WindowUtils.getFrameTimeSeconds();
		moveFactor %= 1.0f;
		shader.enable();
		shader.setMoveFactor(moveFactor);
		shader.setViewMatrix(Matrix4f.ViewMatrix(camera.getViewPoint(), camera.getPosition()));
		shader.setCameraVector(camera.getPosition());
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectionTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, dudvTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionDepthTexture());
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void unbind(){
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glDisable(GL11.GL_BLEND);
		shader.disable();
	}

	private void setUpVAO(Loader loader) {
		// Just x and z vectex positions here, y is set to 0 in v.shader
		float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
		quad = loader.loadToVAO(vertices, 2);
	}

	public void cleanUp() {
		shader.cleanUp();
		fbos.cleanUp();
	}

}