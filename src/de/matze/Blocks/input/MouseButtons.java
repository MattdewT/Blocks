package de.matze.Blocks.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * Input Abfrage der MouseKnöpfe.
 * Ueberschreibt die Inputabfrage.
 *
 * @author matze tiroch
 * @version 1.1
 */

public class MouseButtons extends GLFWMouseButtonCallback {

	public static boolean[] buttons = new boolean[65536];
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		buttons[button] = action != GLFW.GLFW_RELEASE;
	}

}
