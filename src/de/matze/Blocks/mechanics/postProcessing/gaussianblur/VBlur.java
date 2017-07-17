package de.matze.Blocks.mechanics.postProcessing.gaussianblur;

import de.matze.Blocks.mechanics.postProcessing.ImageRenderer;

/**
 * @date 08.10.2016
 * @autor matze
 */

public class VBlur{

	private VBlurShader shader;
	private ImageRenderer imagerenderer;

	public VBlur(int tragetFboWidth, int tragetFboHeight) {
		imagerenderer = new ImageRenderer(tragetFboWidth, tragetFboHeight);
		shader = new VBlurShader();
		shader.enable();
		shader.setTargetWidth(tragetFboWidth);
		shader.disable();
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
