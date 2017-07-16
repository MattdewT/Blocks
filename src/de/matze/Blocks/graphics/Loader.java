package de.matze.Blocks.graphics;

import de.matze.Blocks.utils.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

/**
 * @autor matze
 * @version 1.1
 * @date 06.06.2017
 */

//ToDo: Comments

public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public Loader() {
        this.vaos = new ArrayList<>();
        this.vbos = new ArrayList<>();
        this.textures = new ArrayList<>();
    }

    //================================================= VAO & VBO Basic Functions ======================================

    public int createVAO() {
        int vao = glGenVertexArrays();
        vaos.add(vao);
        glBindVertexArray(vao);
        return vao;
    }

    public void AddAttributeList(float[] data, int attributeNumber, int coordinateSize) {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int bindIndicesBuffer(int[] indices) {
        int ibo = glGenBuffers();
        vbos.add(ibo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL_STATIC_DRAW);
        return ibo;
    }

    public void unbindVAO() {
        glBindVertexArray(0);
    }

    //================================================= Helper Functions for VAOs ======================================

    public VertexArray loadToVAO(float[] verticies, float[] normals, int[] indices) {
        int VaoID = createVAO();
        bindIndicesBuffer(indices);
        AddAttributeList(verticies, 0, 3);
        AddAttributeList(normals, 1, 3);
        unbindVAO();
        return new VertexArray(VaoID, indices.length);
    }

    public VertexArray loadToVAO(float[] verticies, int dimensions) {
        int VaoID = createVAO();
        AddAttributeList(verticies, 0, dimensions);
        unbindVAO();
        return new VertexArray(VaoID, verticies.length / dimensions);
    }

    //================================================= Texture Stuff ======================================

    public int loadCubeMap(String[] textureFiles) {
        int texID = glGenTextures();
        glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);

        for (int i = 0; i < textureFiles.length; i++) {
            TextureData data = decodeTextureFile("res/textures/skybox/" + textureFiles[i] + ".png");
            GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
        }

        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);
        textures.add(texID);
        return texID;
    }

    //ToDo: Copied Function
    public int loadTexture(String path) {
        TextureData data = decodeTextureFile(path);

        int texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, data.getWidth(), data.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data.getBuffer());
        glBindTexture(GL_TEXTURE_2D, 0);
        textures.add(texID);

        return texID;
    }

    private TextureData decodeTextureFile(String path) {
        int[] pixels = null;
        int width = 0, height = 0;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] data = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            assert pixels != null;
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        return new TextureData(BufferUtils.createIntBuffer(data), width, height);
    }

    //================================================= Clean Up Stuff ======================================

    public void cleanUp() {
        for (int vao : vaos) {
            glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
        for (int texture : textures) {
            glDeleteTextures(texture);
        }
    }
}
