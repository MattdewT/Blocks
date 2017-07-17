package de.matze.Blocks.mechanics.postProcessing.gaussianblur;

import de.matze.Blocks.graphics.Shader;

/**
 * @autor matze
 * @date 08.10.2016
 */

public class VBlurShader extends Shader{

	private final static String vertPath = "src/de/matze/Blocks/mechanics/postProcessing/gaussianblur/VBlurShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/postProcessing/gaussianblur/BlurShader.fs";
	
    private int Location_targetWidth;

	public VBlurShader() {
		super(vertPath, fragPath);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		Location_targetWidth = getUniformLocation("targetWidth");
	}
	
	protected void setTargetWidth(float targetWidth) {
		setUniform1f(Location_targetWidth, targetWidth);
	}

}
