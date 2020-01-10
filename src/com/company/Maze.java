package com.company;

public class Maze {

    private int length, width, level;
    private long seed;
    private Cell [][] maze;
    private String algorithm;

    public Maze(int length, int width, int level, long seed, String algorithm) {
        this.length = length;
        this.width = width;
        this.level = level;
        this.seed = seed;
        this.algorithm = algorithm;
        this.maze = new Cell[width][length];
        for(int i = 0; i<width; i++)
            for(int j = 0; j<length; j++)
            {
                maze[i][j] = new Cell();
                maze[i][j].setX(i);
                maze[i][j].setY(j);
            }

    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public Cell[][] getMaze() {
        return maze;
    }

    public void setMaze(Cell[][] maze) {
        this.maze = maze;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void visitedReset() /**Resetuje visited w kazdej komorce na false**/
    {
        for(int i = 0; i<this.width; i++)
            for(int j = 0; j<this.width; j++)
            {
                this.maze[i][j].setVisited(false);
            }
    }





}
