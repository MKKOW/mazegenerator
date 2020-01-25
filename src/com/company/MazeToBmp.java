package com.company;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
// parts from http://www.java2s.com/Tutorials/Java/Graphics_How_to/Image/Create_BMP_format_image.htm

/**
 * Class used to represent mazes as a BMP format image.
 */
public class MazeToBmp {

    private static Maze maze;
    private static BufferedImage res;
    /**
     * Constructor used to generate images of mazes.
     * @param maze maze to recreate in image
     * @param name chosen name of an image
     * @param solved whether the maze was solved or not.
     */
    public MazeToBmp(Maze maze, String name,boolean solved) {
        MazeToBmp.maze = maze;
        res = convertMaze(maze.getMaze(),maze.getWidth(),maze.getLength(), solved);
        saveBMP(name);

    }

    /**
     * Creates red path using information from solved maze (only called when received maze is solved).
     * Checks whether adjacent cells are a part of the path, and whether there is a wall in between.
     * If cell is a part of path and there is no wall, draws a 3 px long red line towards that cell.
     * When method is called at it, a line will be drawn from it, too, connecting them together.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private static void pathMaker(int x, int y){
       try {
           if (maze.getMaze()[x][y].isVisited()) {
               if (x != (maze.getWidth() - 1))
                   if (maze.getMaze()[x + 1][y].isVisited() && !(maze.getMaze()[x][y].isE())) {
                       res.setRGB((5 * x) + 2, (5 * y) + 2, Color.RED.getRGB());
                       res.setRGB((5 * x) + 3, (5 * y) + 2, Color.RED.getRGB());
                       res.setRGB((5 * x) + 4, (5 * y) + 2, Color.RED.getRGB());
                   }
               if (x != 0)
                   if (maze.getMaze()[x - 1][y].isVisited() && !(maze.getMaze()[x][y].isW())) {
                       res.setRGB((5 * x) + 2, (5 * y) + 2, Color.RED.getRGB());
                       res.setRGB((5 * x) + 1, (5 * y) + 2, Color.RED.getRGB());
                       res.setRGB((5 * x), (5 * y) + 2, Color.RED.getRGB());
                   }
               if (y != (maze.getLength() - 1))
                   if (maze.getMaze()[x][y + 1].isVisited() && !(maze.getMaze()[x][y].isN())) {
                       res.setRGB((5 * x) + 2, (5 * y) + 2, Color.RED.getRGB());
                       res.setRGB((5 * x) + 2, (5 * y) + 3, Color.RED.getRGB());
                       res.setRGB((5 * x) + 2, (5 * y) + 4, Color.RED.getRGB());
                   }
               if (y != 0)
                   if (maze.getMaze()[x][y - 1].isVisited() && !(maze.getMaze()[x][y].isS())) {
                       res.setRGB((5 * x) + 2, (5 * y) + 2, Color.RED.getRGB());
                       res.setRGB((5 * x) + 2, (5 * y) + 1, Color.RED.getRGB());
                       res.setRGB((5 * x) + 2, 5 * y, Color.RED.getRGB());
                   }




               }
           } catch (ArrayIndexOutOfBoundsException ignored) {

       }
    }

    /**
     * Creates black corners in image for given cell.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private static void setBlackCorners(int x, int y){
        res.setRGB(5*x,5*y,Color.BLACK.getRGB());
        res.setRGB(5*x+4,5*y,Color.BLACK.getRGB());
        res.setRGB(5*x,5*y+4,Color.BLACK.getRGB());
        res.setRGB(5*x+4,5*y+4,Color.BLACK.getRGB());
    }

    /**
     * Draws black south wall.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private static void drawSWall(int x, int y){
        for (int i =0; i < 3 ;i++)
            res.setRGB((5*x)+1+i, 5*y,Color.BLACK.getRGB());
    }

    /**
     * Draws black north wall.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private static void drawNWall(int x, int y){
        for (int i =0; i < 3 ;i++)
            res.setRGB((5*x)+1+i, (5*y)+4,Color.BLACK.getRGB());
    }

    /**
     * Draws black west wall.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private static void drawWWall(int x, int y){
        for (int i =0; i < 3 ;i++)
            res.setRGB((5*x), (5*y)+1+i ,Color.BLACK.getRGB());
    }

    /**
     * Draws black east wall.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private static void drawEWall(int x, int y){
        for (int i =0; i < 3 ;i++)
            res.setRGB((5*x)+4, (5*y)+1+i,Color.BLACK.getRGB());
    }

    /**
     * Creates BufferedImage object,makes image white, and draws graphic representation of maze(walls,path) .
     * Checks for walls of each cell in maze, and calls according methods.
     * Paints starting cell green, and ending cell blue.
     * If the maze is solved, also calls methods to draw a path of completion.
     * @param maze Two-dimensional 'Cell' object array.
     * @param length length of maze.
     * @param width width of maze.
     * @param solved whether received maze is solved or not.
     * @return Buffered image ready to be saved to a file.
     */
    private static BufferedImage convertMaze(Cell[][] maze, int length, int width, boolean solved) {
        res = new BufferedImage(5*length, 5*width, BufferedImage.TYPE_INT_RGB);
        for (int y=0;y < 5*width; y++) {
            for (int x = 0; x < 5*length; x++) {
            res.setRGB(x,y,Color.WHITE.getRGB());
            }
        }
        for (int y=0;y < width; y++) {
            for (int x = 0; x < length; x++) {
                setBlackCorners(x, y);

                if (maze[x][y].isN()) drawNWall(x, y);
                if (maze[x][y].isS()) drawSWall(x, y);
                if (maze[x][y].isE()) drawEWall(x, y);
                if (maze[x][y].isW()) drawWWall(x, y);

                if (solved)
                pathMaker(x,y);
            }
        }
        for (int i = 0; i < 3;i++)
            for (int j = 0; j < 3; j++)
                res.setRGB(1+i,1+j, Color.GREEN.getRGB());
        for (int i = 0; i < 3;i++)
            for (int j = 0; j < 3; j++)
        res.setRGB((5*(length-1))+1+i , (5*(width-1))+1+j,Color.BLUE.getRGB());
        return res;
    }

    /**
     * Saves buffered image in BMP format.
     * @param name chosen name of a maze.
     */
    private static void saveBMP(final String name ){
        try {
            RenderedImage ri = res;
            ImageIO.write(ri, "bmp", new File(name+".bmp"));

        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

}
