package de.matze.Blocks.mechanics.postProcessing;

/**
 * @autor matze
 * @date 08.10.2016
 */
//TODO change to clear
public class PPRenderer {

	private PPShader shader;
	private ImageRenderer imagerenderer;

	public PPRenderer() {
		imagerenderer = new ImageRenderer();
		shader = new PPShader();
	}
	
	public void render() {
		shader.enable();
		imagerenderer.renderQuad();
		shader.disable();
	}
	
	public int getOutputTexture() {
		return imagerenderer.getOutputTexture();
	}
}
