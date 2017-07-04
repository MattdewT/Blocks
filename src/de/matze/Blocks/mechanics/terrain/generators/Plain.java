package de.matze.Blocks.mechanics.terrain.generators;

/**
 * @autor matze
 * @date 04.07.2017
 */

public class Plain extends HeigthGenerator {

    public Plain() {
        super(GeneratorTypes.Plain);
    }


    @Override
    public float generateHeigth(int x, int z) {
        return 0;
    }
}
