package de.matze.Blocks.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
*Input Abfrage der MausPosition.
*Ueberschreibt die Inputabfrage.
*
*@author matze tiroch
*@version 1.1
*/

public class MousePos extends GLFWCursorPosCallback {

	private static double mouseX = 0;
	private static double mouseY = 0;
	
	private static double mouseDX = 0;
	private static double mouseDY = 0;
	
	private static double oldmouseX = 0;
	private static double oldmouseY = 0;
	
    @Override
    public void invoke(long window, double xpos, double ypos) {
        oldmouseX = mouseX;
        oldmouseY = mouseY;
        
        mouseX = xpos;
        mouseY = ypos;
        
        setMouseDX(oldmouseX - mouseX);
        setMouseDY(oldmouseY - mouseY);
    }

	public static double getMouseDX() {
		return mouseDX;
	}

	public static void setMouseDX(double mouseDX) {
		MousePos.mouseDX = mouseDX;
	}

	public static double getMouseDY() {
		return mouseDY;
	}

	public static void setMouseDY(double mouseDY) {
		MousePos.mouseDY = mouseDY;
	}
	
	public static double getMouseX() {
		return mouseX;
	}

	public static double getMouseY() {
		return mouseY;
	}

}
