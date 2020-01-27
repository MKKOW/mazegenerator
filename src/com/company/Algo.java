package com.company;

import java.util.Random;

import static java.lang.Math.abs;

public class Algo {

    private Random rdm;

    public Algo(long seed)
    {
        rdm = new Random(seed);
    }

    public Algo() {

    }

    public void generator(Maze maze) {

    }

    /**Do dopisania**/
    public Cell Finish(Maze maze, int x, int y){ /**Dajemy mu miejsce startu, labirynt i poziom a on generuje finish**/
        Cell finish = new Cell();

        return finish;
    }
    int randomGenerator() {
        int num = abs(rdm.nextInt() % 4);

        return num;
    }
    public Random getRdm(){
        return rdm;
    }
}
