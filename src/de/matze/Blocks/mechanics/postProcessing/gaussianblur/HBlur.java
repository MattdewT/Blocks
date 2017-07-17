package de.matze.Blocks.mechanics.postProcessing.gaussianblur;

import de.matze.Blocks.mechanics.postProcessing.ImageRenderer;

/**
 * @date 08.10.2016
 * @autor matze
 */

public class HBlur{
	
	private HBlurShader shader;
	private ImageRenderer imagerenderer;

	public HBlur(int targetFboWidth, int targetFboHeight) {
		imagerenderer = new ImageRenderer(targetFboWidth, targetFboHeight);
		shader = new HBlurShader();
		shader.enable();
		shader.setTargetHeight(targetFboHeight);
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
