package de.matze.Blocks.main;

import de.matze.Blocks.entities.GameObject;
import de.matze.Blocks.entities.components.*;
import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.input.Keyboard;
import de.matze.Blocks.input.MouseButtons;
import de.matze.Blocks.input.MousePos;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.mechanics.terrain.TerrainRenderer;
import de.matze.Blocks.mechanics.terrain.TerrainShader;
import de.matze.Blocks.mechanics.terrain.TerrainTile;
import de.matze.Blocks.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

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
    float width = 100;
    float height = 100;
    float getX() {
        return 10;
    }
    float getY() {
        return 10;
    }

    GameObject Player;

    //Terrain Test
    List<TerrainTile> terrains;
    TerrainRenderer terrainRenderer;
    Loader loader;
    TerrainShader terrainShader;

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
        terrainRenderer.cleanUp();
        loader.cleanUp();
    }

    private void init() {
        if(glfwInit() == GL_FALSE) {
            throw new IllegalStateException("GLFW failed to init");
        }

        keyboard = new Keyboard();
        mouseButtons = new MouseButtons();
        mousePos = new MousePos();

        WindowUtils.init(keyboard, mousePos,mouseButtons);

        Player = new GameObject(0);
        Player.addComponent(new TransformComponent(new Vector3f(0, 0, 0)));
        Player.addComponent(new ViewComponent());
        Player.addComponent(new CameraComponent(Player));
        Player.addComponent(new PlayerComponent(Player));

        loader = new Loader();
        terrains = new ArrayList<>();
        terrainShader = new TerrainShader();
        terrainRenderer = new TerrainRenderer(Matrix4f.orthographic_copied(0, 400, 0, 300, 10, -10), terrainShader);
//        terrainRenderer = new TerrainRenderer(Matrix4f.gluPerspective(68f, WindowUtils.getWidth() / WindowUtils.getHeight(), 0.3f, 1200.0f),terrainShader);
        terrains.add(new TerrainTile(0, 0, loader));

        //Projectionmatrix setzen
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
//        glOrtho(0, 400, 0, 300, 1, -1);
        glLoadMatrixf(Matrix4f.orthographic_copied(-200, 200, -150, 150, 100, -100).toFloatBuffer());
        glMatrixMode(GL_MODELVIEW);
    }

    private void update() {
        glfwPollEvents();

        ((PlayerComponent) Player.getComponent(Component.ComponentTypes.Player)).update();

        WindowUtils.update();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        terrainShader.enable();
        terrainShader.setViewMatrix(((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).getViewMatrix());
        terrainRenderer.render(terrains);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glColor3f(0,1,1);

        glBegin(GL_QUADS);
        glVertex3f(getX(),getY(),1);
        glVertex3f(getX()+width,getY(),1);
        glVertex3f(getX()+width,getY()+height,1);
        glVertex3f(getX(),getY()+height,1);
        glEnd();

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        terrainShader.disable();

//        glColor3f(0,1,1);
//
//        glBegin(GL_QUADS);
//        glVertex3f(getX(),getY(),-1);
//        glVertex3f(getX()+width,getY(),-1);
//        glVertex3f(getX()+width,getY()+height,-1);
//        glVertex3f(getX(),getY()+height,-1);
//        glEnd();

        glfwSwapBuffers(WindowUtils.getWindow());
    }

    public static void main(String args[]) {
        new BasicWindow().start();
    }
}
