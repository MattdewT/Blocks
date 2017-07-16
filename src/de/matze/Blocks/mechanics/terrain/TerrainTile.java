package de.matze.Blocks.mechanics.terrain;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.mechanics.terrain.generators.HeigthGenerator;
import de.matze.Blocks.mechanics.terrain.generators.Perlin;

/**
 * @author matze tiroch
 * @version 1.0
 * Created by matze on 05.09.16.
 */

//ToDo: check class => Terrain Master
//ToDo: rearenage to gameobject

public class TerrainTile {

    protected static final float SIZE = 100;
    protected static final int VERTEX_COUNT = 32;

    private float x;
    private float z;
    private VertexArray model;
    private Matrix4f ml_matrix;

    public TerrainTile(int gridX, int gridZ, Loader loader, int seed) {
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader, new Perlin(gridX, gridZ, VERTEX_COUNT, seed));
        ml_matrix = Matrix4f.translate(x, 0, z);
    }

    private VertexArray generateTerrain(Loader loader, HeigthGenerator heigthGenerator) {

        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT * 1)];
        int vertexPointer = 0;
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {

                vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;                           //calculate VertexPositions
                vertices[vertexPointer * 3 + 1] = getHeight(j,i, heigthGenerator);
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;

                Vector3f normal = calculateNormal(j,i, heigthGenerator);                                                //set Normals
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;

                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {                                                                 //calculate Indices
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, normals, indices);
    }

    public float getX() {
        return x;
    }


    public float getZ() {
        return z;
    }

    public VertexArray getModel() {
        return model;
    }

    public Matrix4f getModelMatrix() {
        return ml_matrix;
    }

    private Vector3f calculateNormal(int x, int z, HeigthGenerator generator){
        float heightL = getHeight(x-1, z, generator);
        float heightR = getHeight(x+1, z, generator);
        float heightD = getHeight(x, z-1, generator);
        float heightU = getHeight(x, z+1, generator);
        Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD - heightU);
        normal.normalize(normal);
        return normal;
    }

    private float getHeight(int x, int z, HeigthGenerator generator){
        return generator.generateHeigth(x, z);
    }
}
