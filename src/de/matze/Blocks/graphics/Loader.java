package de.matze.Blocks.graphics;

import de.matze.Blocks.utils.BufferUtils;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

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

    //================================================= Texture Stuff ======================================

    //ToDo: Texture Stuff

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
