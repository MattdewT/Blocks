package de.matze.Blocks.main;

import de.matze.Blocks.input.Keyboard;
import de.matze.Blocks.input.MouseButtons;
import de.matze.Blocks.input.MousePos;
import de.matze.Blocks.utils.WindowUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

/**
 * @autor matze
 * @date 30.05.2017
 */

public class BasicWindow implements Runnable{

    private boolean running;
    private Thread thread;

    private Keyboard keyboard;
    private MouseButtons mouseButtons;
    private MousePos mousePos;

    //Test Stuff
    float width = 10;
    float height = 10;
    float getX() {
        return 10;
    }
    float getY() {
        return 10;
    }

    public  void start() {
        running = true;
        thread = new Thread(this, "BasicWindow");
        thread.start();
    }

    @Override
    public void run() {
        init();
        while(running) {
            render();
            update();
            if(glfwWindowShouldClose(WindowUtils.getWindow()) == GL_TRUE)
                running = false;
        }
        WindowUtils.cleanUp();
    }

    private void init() {
        if(glfwInit() == GL_FALSE) {
            throw new IllegalStateException("GLFW failed to init");
        }

        keyboard = new Keyboard();
        mouseButtons = new MouseButtons();
        mousePos = new MousePos();

        WindowUtils.init(keyboard, mousePos,mouseButtons);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 400, 0, 300, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    private void update() {
        glfwPollEvents();
        WindowUtils.update();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//        glClearColor(0.0f, 1.0f, 0.0f, 1.0f);

        //Test Stuff
        glColor3f(1.0f,1.0f,0.0f);

        glBegin(GL_QUADS);
        glVertex2f(getX(),getY());
        glVertex2f(getX()+width,getY());
        glVertex2f(getX()+width,getY()+height);
        glVertex2f(getX(),getY()+height);
        glEnd();


        glfwSwapBuffers(WindowUtils.getWindow());
    }

    public static void main(String args[]) {
        new BasicWindow().start();
    }
}
