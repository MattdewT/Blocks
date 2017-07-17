package de.matze.Blocks.mechanics.postProcessing.gaussianblur;

import de.matze.Blocks.graphics.Shader;

/**
 * @autor matze
 * @date 08.10.2016
 */

public class HBlurShader extends Shader{

	private final static String vertPath = "src/de/matze/Blocks/mechanics/postProcessing/gaussianblur/HBlurShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/postProcessing/gaussianblur/BlurShader.fs";
	
    private int Location_targetHeight;

	public HBlurShader() {
		super(vertPath, fragPath);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		Location_targetHeight = getUniformLocation("targetHeight");
	}
	
	protected void setTargetHeight(float targetHeight) {
		setUniform1f(Location_targetHeight, targetHeight);
	}

}
