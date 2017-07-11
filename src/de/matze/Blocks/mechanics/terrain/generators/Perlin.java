package de.matze.Blocks.mechanics.terrain.generators;

import java.util.Random;

/**
 * @autor matze
 * @date 04.07.2017
 */

//ToDo: Copied Class

public class Perlin  extends HeigthGenerator {

    private static final float AMPLITUDE = 30f;
    private static final float OCTAVES = 3;
    private static final float ROUGHNESS = 0.3f;

    private static final float inner_circle = 45;
    private static final float outer_circle = 60;

    private Random random = new Random();
    private int seed;

    private int xOffset;
    private int zOffset;

    public Perlin(int gridX, int gridZ, int vertexCount, int seed) {
        super(GeneratorTypes.Perlin);
        this.seed = seed;
        this.xOffset = gridX * (vertexCount - 1);
        this.zOffset = gridZ * (vertexCount - 1);
    }

    public Perlin() {
        super(GeneratorTypes.Perlin);
        this.seed = random.nextInt(1000000000);
        this.xOffset = 0;
        this.zOffset = 0;
    }

    @Override
    public float generateHeigth(int x, int z) {
        float amp_mul = 1;
        float distance = (float) Math.sqrt((inner_circle - (x + xOffset)) * (inner_circle - (x + xOffset)) + (inner_circle - (z + zOffset)) * (inner_circle - (z + zOffset)));  //calculate the distance and
        if(distance > inner_circle && distance < outer_circle){                                                                                                                 //decide which is inside the circle
            amp_mul *= 1 - (distance - inner_circle) / (outer_circle - inner_circle);
        } else if(distance >= outer_circle) {
            amp_mul = 0.1f;
        }

        float total = 0;
        float d = (float) Math.pow(2, OCTAVES - 1);                                                                                                                             //generate noise
        for(int i = 0; i < OCTAVES; i++) {
            float freq = (float) (Math.pow(2, i) / d);
            float amp = (float) Math.pow(ROUGHNESS, i) * AMPLITUDE;
            total += getInterpolatedNoise((x + xOffset) * freq, (z + zOffset) * freq) * amp;

            total = total * amp_mul - 15 * (1 - amp_mul);                                                                                                                      //apply circle
        }

        return total;
    }

    public float getNoise(int x, int z) {
        random.setSeed(x * 34003 + z * 67515 + seed);
        return random.nextFloat() * 2f - 1f;
    }

    private float getSmothNoise(int x, int z) {
        float corners = (getNoise(x-1, z-1) + getNoise(x+1, z-1) + getNoise(x-1, z+1) + getNoise(x+1, z+1)) / 16f;
        float sides = (getNoise(x-1, z) + getNoise(x+1, z) + getNoise(x, z-1) + getNoise(x, z+1)) / 8f;
        float center = getNoise(x, z) / 4f;
        return corners + sides + center;
    }

    private float getInterpolatedNoise(float x, float z) {
        int intX = (int) x;
        int intZ = (int) z;
        float fracX = x - intX;
        float fracZ = z - intZ;

        float v1 = getSmothNoise(intX, intZ);
        float v2 = getSmothNoise(intX + 1, intZ);
        float v3 = getSmothNoise(intX, intZ + 1);
        float v4 = getSmothNoise(intX + 1, intZ + 1);
        float i1 = interpolate(v1, v2, fracX);
        float i2 = interpolate(v3, v4, fracX);
        return interpolate(i1, i2, fracZ);
    }

    private float interpolate(float a, float b, float blend) {
        double theta = blend * Math.PI;
        float f = (float) (1f - Math.cos(theta)) * 0.5f;
        return a * (1f - f) + b * f;
    }

}