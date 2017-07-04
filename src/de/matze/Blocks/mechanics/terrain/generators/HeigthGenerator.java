package de.matze.Blocks.mechanics.terrain.generators;

/**
 * @autor matze
 * @date 04.07.2017
 */

public abstract class HeigthGenerator {

    private GeneratorTypes generatorType;

    public enum GeneratorTypes {
        Perlin, Plain;
    }

    public HeigthGenerator(GeneratorTypes generatorType) {
        this.generatorType = generatorType;
    }

    public GeneratorTypes getGeneratorType() {
        return generatorType;
    }

    public abstract float generateHeigth(int x, int z);
}
