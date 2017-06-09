package de.matze.Blocks.utils;

import de.matze.Blocks.input.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Creates a Window, managed the input and the dalta time
 *
 * @author matze tiroch
 * @version 1.2
 * Created by matze on 24.08.16.
 */

//ToDo: Comments

public class WindowUtils {

    private static int width = 800;
    private static int height = 600;

    private static long window;

    private static long lastFrameTime;
    private static float delta;

    private WindowUtils() {}

    public static void init(Keyboard input, MousePos mousepos, MouseButtons mousebuttons) {
        //Set Window Properties
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        //Set Opengl Version
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);

        //Create Window
        window = glfwCreateWindow(width, height, "Blocks", NULL, NULL);

        if (window == NULL) {
            System.out.println("Failed to create GLFW Window");
            return;
        }


        //Fenster zentrieren
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);

        //Input
        glfwSetKeyCallback(window, input);
        glfwSetCursorPosCallback(window, mousepos);
        glfwSetMouseButtonCallback(window, mousebuttons);

//        textInput = new TextInput();
//        glfwSetCharCallback(window, textInput);

        //Contex setzen
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GLContext.createFromCurrent();

        System.out.println("OpenGL: " + glGetString(GL_VERSION));
    }

    public static void update() {
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;
    }

    public  static long getWindow() {
        return window;
    }

    /**
     * returns passed time  since the last frame in seconds
     * @return
     */

    public static float getFrameTimeSeconds() {
        return delta;
    }

    private static long getCurrentTime() {
        return (long) (GLFW.glfwGetTime() * 1000);
    }
    
    public static int getWidth() {
    	return width;
    }
    
    public static int getHeight() {
    	return height;
    }

    public static void resetFrameTimeSeconds() {
        delta = 0;
    }

    public static void cleanUp() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

}


