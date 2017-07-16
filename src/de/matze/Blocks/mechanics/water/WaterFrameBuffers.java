package de.matze.Blocks.mechanics.water;

//ToDo: Copied Class

import de.matze.Blocks.graphics.Fbo;
import de.matze.Blocks.utils.WindowUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class WaterFrameBuffers {

	private static final int REFLECTION_WIDTH = 320;
    private static final int REFLECTION_HEIGHT = 180;
     
    private static final int REFRACTION_WIDTH = 1280;
    private static final int REFRACTION_HEIGHT = 720;
    
    private Fbo reflectionBuffer;
    private Fbo refractionBuffer;
    
    public WaterFrameBuffers() {
    	reflectionBuffer = new Fbo(REFLECTION_WIDTH, REFLECTION_HEIGHT, Fbo.DEPTH_RENDER_BUFFER);
    	refractionBuffer = new Fbo(REFRACTION_WIDTH, REFRACTION_HEIGHT, Fbo.DEPTH_TEXTURE);
    	
    }
    
    public int getReflectionTexture() {//get the resulting texture
        return reflectionBuffer.getColourTexture();
    }
     
    public int getRefractionTexture() {//get the resulting texture
        return refractionBuffer.getColourTexture();
    }
     
    public int getRefractionDepthTexture(){//get the resulting depth texture
        return refractionBuffer.getDepthTexture();
    }
    
    public void bindRefractionFrameBuffer() {
    	refractionBuffer.bindFrameBuffer();
    }
    
    public void bindReflectionFrameBuffer() {
    	reflectionBuffer.bindFrameBuffer();
    }
    
    public void unbindFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, WindowUtils.getWidth(), WindowUtils.getHeight());
    }

    public void cleanUp() {
        reflectionBuffer.cleanUp();
        refractionBuffer.cleanUp();
    }
	
}
