package de.matze.Blocks.mechanics.postProcessing;

import de.matze.Blocks.graphics.Shader;

public class PPShader extends Shader{

	private final static String vertPath = "src/de/matze/Blocks/mechanics/postProcessing/postProcessingShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/postProcessing/postProcessingShader.fs";
	
	public PPShader() {
		super(vertPath, fragPath);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {		
	}

}
