package com.company;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
// parts from http://www.java2s.com/Tutorials/Java/Graphics_How_to/Image/Create_BMP_format_image.htm
public class MazeToBmp {

    private static Maze maze;

    public MazeToBmp(){}
    public MazeToBmp(Maze maze, String name) {
        MazeToBmp.maze = maze;
        saveBMP(convertMaze(maze.getMaze(),maze.getLength(),maze.getWidth()),name);

    }

    private static void setBlackCorners(BufferedImage res, int x, int y){
        res.setRGB(5*x,5*y,Color.BLACK.getRGB());
        res.setRGB(5*x+4,5*y,Color.BLACK.getRGB());
        res.setRGB(5*x,5*y+4,Color.BLACK.getRGB());
        res.setRGB(5*x+4,5*y+4,Color.BLACK.getRGB());
    }

    private static void setNWall(BufferedImage res, int x, int y){
        for (int i =0; i < 3 ;i++)
            res.setRGB((5*x)+1+i, 5*y,Color.BLACK.getRGB());
    }
    private static void setSWall(BufferedImage res, int x, int y){
        for (int i =0; i < 3 ;i++)
            res.setRGB((5*x)+1+i, (5*y)+4,Color.BLACK.getRGB());
    }
    private static void setWWall(BufferedImage res, int x, int y){
        for (int i =0; i < 3 ;i++)
            res.setRGB((5*x), (5*y)+1+i ,Color.BLACK.getRGB());
    }
    private static void setEWall(BufferedImage res, int x, int y){
        for (int i =0; i < 3 ;i++)
            res.setRGB((5*x)+4, (5*y)+1+i,Color.BLACK.getRGB());
    }


    private static BufferedImage convertMaze(Cell[][] maze, int length, int width) {
        BufferedImage res = new BufferedImage(5*length, 5*width, BufferedImage.TYPE_INT_RGB);
        for (int y=0;y < 5*width; y++) {
            for (int x = 0; x < 5*length; x++) {
            res.setRGB(x,y,Color.WHITE.getRGB());
            }
        }
        for (int y=0;y < width; y++) {
            for (int x = 0; x < length; x++) {
                setBlackCorners(res, x, y);
                if (maze[x][y].isN()) setNWall(res, x, y);
                if (maze[x][y].isS()) setSWall(res, x, y);
                if (maze[x][y].isE()) setEWall(res, x, y);
                if (maze[x][y].isW()) setWWall(res, x, y);
            }
        }
        return res;
    }


    @SuppressWarnings("UnnecessaryLocalVariable")
    private static void saveBMP(final BufferedImage bi, final String path ){
        try {
            RenderedImage ri = bi;
            ImageIO.write(ri, "bmp", new File(path));

        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

}
