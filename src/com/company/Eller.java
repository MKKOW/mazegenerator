package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Eller extends Algo {
    public Eller(int seed){
        super(seed);
    }

    @Override
    public void generator(Maze maze) {
            int[][] setNumber=new int[maze.getWidth()][maze.getLength()];
            int nSet=1;
            maze.visitedReset();
            for(int j=0;j<maze.getLength()-1;j++) {
                for (int i = 0; i < maze.getWidth(); i++) {
                    if (!maze.getMaze()[i][j].isVisited()) {
                        setNumber[i][j] = nSet;
                        nSet++;
                    }
                }
                for (int i = 0; i < maze.getWidth() - 1; i++) {
                    boolean isWall = getRdm().nextBoolean();
                    if (!isWall && setNumber[i][j] != setNumber[i + 1][j]) {
                        maze.getMaze()[i][j].setE(false);
                        maze.getMaze()[i + 1][j].setW(false);
                        maze.getMaze()[i][j].setVisited(true);
                        maze.getMaze()[i + 1][j].setVisited(true);
                        setNumber[i + 1][j] = setNumber[i][j];
                    }
                }
                int setCount = 0;
                int current = 0;
                for (int i = 0; i < maze.getWidth(); i++) {
                    if (setNumber[i][j] != current) {
                        setCount++;
                        current = setNumber[i][j];
                    }
                }
                int[] setWidth = new int[setCount];
                for (int i = 0; i < setCount; i++) {
                    setWidth[i] = 0;
                }
                setCount = 0;
                current = setNumber[0][j];
                for (int i = 0; i < maze.getWidth(); i++) {
                    if (setNumber[i][j] == current) {
                        setWidth[setCount]++;
                    }
                    if (setNumber[i][j] != current) {
                        setCount++;
                        setWidth[setCount]++;
                        current = setNumber[i][j];
                    }
                }
                int x = 0;
                for (int i = 0; i < setWidth.length; i++) {
                    int downLinksCount = getRdm().nextInt(setWidth[i]) + 1;
                    for (int k = 0; k < downLinksCount; k++) {
                        int downLink = x + getRdm().nextInt(setWidth[i]);
                        maze.getMaze()[downLink][j].setN(false);
                        maze.getMaze()[downLink][j + 1].setS(false);
                        maze.getMaze()[downLink][j].setVisited(true);
                        maze.getMaze()[downLink][j + 1].setVisited(true);
                        setNumber[downLink][j + 1] = setNumber[downLink][j];
                    }
                    x += setWidth[i];
                }
            }
            int y=maze.getLength()-1;
        for (int i = 0; i < maze.getWidth(); i++) {
            if (!maze.getMaze()[i][y].isVisited()) {
                setNumber[i][y] = nSet;
                nSet++;
            }
        }
            for(int i=0;i<maze.getWidth()-1;i++){
                if(setNumber[i][y]!=setNumber[i+1][y]) {
                    maze.getMaze()[i][y].setE(false);
                    maze.getMaze()[i + 1][y].setW(false);
                }
            }
    }
}
