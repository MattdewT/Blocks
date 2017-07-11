package de.matze.Blocks.main;

import de.matze.Blocks.entities.GameObject;
import de.matze.Blocks.entities.components.*;
import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.input.Keyboard;
import de.matze.Blocks.input.MouseButtons;
import de.matze.Blocks.input.MousePos;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.mechanics.skybox.Skybox;
import de.matze.Blocks.mechanics.terrain.TerrainRenderer;
import de.matze.Blocks.mechanics.terrain.TerrainShader;
import de.matze.Blocks.mechanics.terrain.TerrainTile;
import de.matze.Blocks.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

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

    GameObject Player;

    //Terrain Test
    List<TerrainTile> terrains;
    TerrainRenderer terrainRenderer;
    Loader loader;
    TerrainShader terrainShader;

    //Skybox Test
    Skybox skybox;

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

        glEnable(GL_DEPTH_TEST);

        Player = new GameObject(0);
        Player.addComponent(new TransformComponent(new Vector3f(0, 0, 0)));
        Player.addComponent(new ViewComponent());
        Player.addComponent(new CameraComponent(Player));
        Player.addComponent(new PlayerComponent(Player));

        loader = new Loader();
        terrainShader = new TerrainShader();
//        terrainRenderer = new TerrainRenderer(Matrix4f.orthographic(-200, 200, -150, 150, 0.3f, 1200),terrainShader);
        terrainRenderer = new TerrainRenderer(Matrix4f.perspective(68f, (float)WindowUtils.getWidth() / (float)WindowUtils.getHeight(), 0.3f, 1200.0f),terrainShader);

        terrains = new ArrayList<>();

        terrains.add(new TerrainTile(0, 0, loader));
        terrains.add(new TerrainTile(1, 0, loader));
        terrains.add(new TerrainTile(1, 1, loader));
        terrains.add(new TerrainTile(0, 1, loader));

        terrains.add(new TerrainTile(2, 0, loader));
        terrains.add(new TerrainTile(2, 1, loader));
        terrains.add(new TerrainTile(2, 2, loader));
        terrains.add(new TerrainTile(1, 2, loader));
        terrains.add(new TerrainTile(0, 2, loader));

        skybox = new Skybox(loader, Matrix4f.perspective(68f, (float)WindowUtils.getWidth() / (float)WindowUtils.getHeight(), 0.3f, 1200.0f));
    }

    private void update() {
        glfwPollEvents();

        ((PlayerComponent) Player.getComponent(Component.ComponentTypes.Player)).update();

        WindowUtils.update();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        skybox.render(((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).getViewPoint());

        terrainShader.enable();
        terrainShader.setViewMatrix(((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).getViewMatrix());
        terrainRenderer.render(terrains);
        terrainShader.disable();

        glfwSwapBuffers(WindowUtils.getWindow());
    }

    public static void main(String args[]) {
        new BasicWindow().start();
    }
}
