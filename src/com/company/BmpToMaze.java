package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class used to read BMP file and extract maze in solver-friendly form, based on MazeToBmp class.
 * To succeed in this, image must show a maze in the same format as used in image generation (5px x 5px cells. Possible paths - white, 3px wide. Not a single cell corner can be white.)
 */
public class BmpToMaze {

    /**
     * Created maze getter.
     * @return generated maze.
     */
    public Maze getMaze() {
        return maze;
    }

    private Maze maze;

    /**
     * Checks if pixels corresponding to south wall in cell represent it.
     * If there is at least one pixel white, sets cell's wall as nonexistent.
     * @param res Image read from file.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private void getSWall(BufferedImage res, int x, int y){
        if (    res.getRGB((5*x)+1, 5*y) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+2,5*y) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+3,5*y) == Color.WHITE.getRGB() )
            maze.getMaze()[x][y].setS(false);
        else
            maze.getMaze()[x][y].setS(true);
    }
    /**
     * Checks if pixels corresponding to north wall in cell represent it.
     * If there is at least one pixel white, sets cell's wall as nonexistent.
     * @param res Image read from file.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private void getNWall(BufferedImage res, int x, int y){
        if (    res.getRGB((5*x)+1, (5*y)+4) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+2,(5*y)+4) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+3,(5*y)+4) == Color.WHITE.getRGB() )
            maze.getMaze()[x][y].setN(false);
        else
            maze.getMaze()[x][y].setN(true);
    }
    /**
     * Checks if pixels corresponding to east wall in cell represent it.
     * If there is at least one pixel white, sets cell's wall as nonexistent.
     * @param res Image read from file.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private void getEWall(BufferedImage res, int x, int y){
        if (    res.getRGB((5*x)+4, (5*y)+1) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+4,(5*y)+2) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+4,(5*y)+3) == Color.WHITE.getRGB() )
            maze.getMaze()[x][y].setE(false);
        else
            maze.getMaze()[x][y].setE(true);
    }
    /**
     * Checks if pixels corresponding to west wall in cell represent it.
     * If there is at least one pixel white, sets cell's wall as nonexistent.
     * @param res Image read from file.
     * @param x first coordinate of a cell in maze
     * @param y second coordinate of a cell in maze
     */
    private void getWWall(BufferedImage res, int x, int y){
        if (    res.getRGB(5*x, (5*y)+1) == Color.WHITE.getRGB() &&
                res.getRGB(5*x,(5*y)+2) == Color.WHITE.getRGB() &&
                res.getRGB(5*x,(5*y)+3) == Color.WHITE.getRGB() )
            maze.getMaze()[x][y].setW(false);
        else
            maze.getMaze()[x][y].setW(true);
    }

    /**
     * Magic method - if the format of an image is incorrect, takes dimensions of an image and its hash to generate a new maze.
     * @param bi Read image.
     */
    private void magic(BufferedImage bi) {
        System.out.println("Incorrect image format.");
        System.out.println("Magic time!");
        //magic
        Backtracker BT = new Backtracker(bi.hashCode());
        Maze magicMaze = new Maze(bi.getHeight() / 5, bi.getWidth() / 5 , 3, bi.hashCode() , "Backtracker");
        BT.generator(magicMaze, 0, 0);
        this.maze = magicMaze;
    }


    /**
     * Checks whether cell's corners are
     * @param bi Read image.
     * @param x first coordinate of a cell in maze.
     * @param y second coordinate of a cell in maze.
     * @return False if at least one corner is white.
     */
    private boolean checkCorners(BufferedImage bi, int x, int y) {
        return bi.getRGB(5 * x, 5 * y) != Color.WHITE.getRGB() &&
                bi.getRGB(5 * x + 4, 5 * y) != Color.WHITE.getRGB() &&
                bi.getRGB(5 * x, 5 * y + 4) != Color.WHITE.getRGB() &&
                bi.getRGB(5 * x + 4, 5 * y + 4) != Color.WHITE.getRGB();
    }

    /**
     * Constructor. Reads a file, if its format is correct, attempts to decode image, else - calls magic method.
     * @param file image file.
     * @throws IOException when failing during file read.
     */
    public BmpToMaze(File file) throws IOException {
        BufferedImage bi = ImageIO.read(file);
        if (bi.getHeight() % 5 != 0 || bi.getWidth() % 5 != 0) {
            magic(bi);
        }
        else {
            maze = decodeBMPToMaze(bi);
        }
    }

    /**
     * Decodes BMP file to create a maze.
     * @param bi Read image.
     * @return Created maze.
     */
    private Maze decodeBMPToMaze(BufferedImage bi) {
        int length = bi.getHeight() / 5;
        int width = bi.getWidth() / 5;
        maze=new Maze(length,width,1,1,"unknown");
        maze.setLength(length);
        maze.setWidth(width);
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < length; x++) {
                if (!(checkCorners(bi, x, y))) {
                    magic(bi);
                    return maze;
                }
                getNWall(bi,x,y);
                getSWall(bi,x,y);
                getWWall(bi,x,y);
                getEWall(bi,x,y);
            }

        }
    return maze;
    }
}
