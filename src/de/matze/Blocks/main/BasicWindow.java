package de.matze.Blocks.main;

import de.matze.Blocks.entities.GameObject;
import de.matze.Blocks.entities.components.*;
import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.input.Keyboard;
import de.matze.Blocks.input.MouseButtons;
import de.matze.Blocks.input.MousePos;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.maths.Vector4f;
import de.matze.Blocks.mechanics.skybox.Skybox;
import de.matze.Blocks.mechanics.terrain.TerrainRenderer;
import de.matze.Blocks.mechanics.terrain.TerrainShader;
import de.matze.Blocks.mechanics.terrain.TerrainTile;
import de.matze.Blocks.mechanics.water.WaterFrameBuffers;
import de.matze.Blocks.mechanics.water.WaterRenderer;
import de.matze.Blocks.mechanics.water.WaterShader;
import de.matze.Blocks.mechanics.water.WaterTile;
import de.matze.Blocks.utils.FileUtils;
import de.matze.Blocks.utils.WindowUtils;
import org.lwjgl.opengl.GL30;

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

    //WaterTest
    List<WaterTile> waterTiles;
    WaterRenderer waterRenderer;
    WaterShader waterShader;
    WaterFrameBuffers waterFrameBuffers;

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
        waterRenderer.cleanUp();
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

        Matrix4f pr_matrix = Matrix4f.perspective(68f, (float)WindowUtils.getWidth() / (float)WindowUtils.getHeight(), 0.3f, 1200.0f);

        terrainShader = new TerrainShader();
        terrainRenderer = new TerrainRenderer(pr_matrix,terrainShader);

        terrains = new ArrayList<>();

        int seed = Integer.parseInt(FileUtils.loadAsString("res/seed").split("\n")[0]);

        terrains.add(new TerrainTile(0, 0, loader, seed));
        terrains.add(new TerrainTile(1, 0, loader, seed));
        terrains.add(new TerrainTile(1, 1, loader, seed));
        terrains.add(new TerrainTile(0, 1, loader, seed));
        terrains.add(new TerrainTile(2, 0, loader, seed));
        terrains.add(new TerrainTile(2, 1, loader, seed));
        terrains.add(new TerrainTile(2, 2, loader, seed));
        terrains.add(new TerrainTile(1, 2, loader, seed));
        terrains.add(new TerrainTile(0, 2, loader, seed));

        waterShader = new WaterShader();
        waterFrameBuffers = new WaterFrameBuffers();
        waterRenderer = new WaterRenderer(loader, waterShader, pr_matrix, waterFrameBuffers,  0.3f, 1200.0f);

        waterTiles = new ArrayList<>();

        waterTiles.add(new WaterTile(1, 0, 1));
        waterTiles.add(new WaterTile(1, 0, 3));
        waterTiles.add(new WaterTile(1, 0, 5));
        waterTiles.add(new WaterTile(3, 0, 1));
        waterTiles.add(new WaterTile(3, 0, 3));
        waterTiles.add(new WaterTile(3, 0, 5));
        waterTiles.add(new WaterTile(5, 0, 1));
        waterTiles.add(new WaterTile(5, 0, 3));
        waterTiles.add(new WaterTile(5, 0, 5));

        skybox = new Skybox(loader, pr_matrix);
    }

    private void update() {
        glfwPollEvents();

        ((PlayerComponent) Player.getComponent(Component.ComponentTypes.Player)).update();

        if(Keyboard.keys[GLFW_KEY_G]) {
            terrains.clear();
            int seed = (int) (Math.random() * 1000000);
            terrains.add(new TerrainTile(0, 0, loader, seed));
            terrains.add(new TerrainTile(1, 0, loader, seed));
            terrains.add(new TerrainTile(1, 1, loader, seed));
            terrains.add(new TerrainTile(0, 1, loader, seed));
            terrains.add(new TerrainTile(2, 0, loader, seed));
            terrains.add(new TerrainTile(2, 1, loader, seed));
            terrains.add(new TerrainTile(2, 2, loader, seed));
            terrains.add(new TerrainTile(1, 2, loader, seed));
            terrains.add(new TerrainTile(0, 2, loader, seed));

            FileUtils.saveAsString("res/seed", seed + "\n");
        }

        WindowUtils.update();
    }

    private void render() {
        glEnable(GL30.GL_CLIP_DISTANCE0);

        waterFrameBuffers.bindReflectionFrameBuffer();      //Reflection Render Call
        float distance = (((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).getPosition().y - waterTiles.get(0).getHeight()) * 2;
        ((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).getPosition().y -= distance;
        ((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).invertPitch();
        renderScene(new Vector4f(0, 1, 0, - waterTiles.get(0).getHeight()));
        ((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).getPosition().y += distance;
        ((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).invertPitch();

        waterFrameBuffers.bindRefractionFrameBuffer();      //Refraction Render Call
        renderScene(new Vector4f(0, -1, 0, waterTiles.get(0).getHeight() + 1));

        waterFrameBuffers.unbindFrameBuffer();              //Normal Render Call

        glDisable(GL30.GL_CLIP_DISTANCE0);

        renderScene(new Vector4f(0, 1, 0, 100000));

        waterRenderer.render(waterTiles, (CameraComponent) Player.getComponent(Component.ComponentTypes.Camera));

        glfwSwapBuffers(WindowUtils.getWindow());
    }

    private void renderScene(Vector4f clipplane) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        skybox.render(((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).getViewPoint());

        terrainShader.enable();
        terrainShader.setViewMatrix(((CameraComponent) Player.getComponent(Component.ComponentTypes.Camera)).getViewMatrix());
        terrainRenderer.render(terrains, clipplane);
        terrainShader.disable();
    }

    public static void main(String args[]) {
        new BasicWindow().start();
    }
}
