package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Klasa odpowiadająca za odczyt pliku bmp i generację labiryntu.
 * Plik powinien zawierać labirynt w odpowiednim formacie, takim samym jak przy generowaniu obrazka labiryntu. (Każda komórka w formacie 5px x 5px. Możliwe ścieżki muszą być białe, o szerokości 3 pikseli. Żaden róg żadnej komórki nie może być biały.)
 */
public class BmpToMaze {

    /**
     * Funkcja do odbierania odczytanego labiryntu.
     * @return Odczytany labirynt.
     */
    public Maze getMaze() {
        return maze;
    }

    private Maze maze;

    private void getSWall(BufferedImage res, int x, int y){
        if (    res.getRGB((5*x)+1, 5*y) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+2,5*y) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+3,5*y) == Color.WHITE.getRGB() )
            maze.getMaze()[x][y].setS(false);
        else
            maze.getMaze()[x][y].setS(true);
    }
    private void getNWall(BufferedImage res, int x, int y){
        if (    res.getRGB((5*x)+1, (5*y)+4) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+2,(5*y)+4) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+3,(5*y)+4) == Color.WHITE.getRGB() )
            maze.getMaze()[x][y].setN(false);
        else
            maze.getMaze()[x][y].setN(true);
    }
    private void getEWall(BufferedImage res, int x, int y){
        if (    res.getRGB((5*x)+4, (5*y)+1) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+4,(5*y)+2) == Color.WHITE.getRGB() &&
                res.getRGB((5*x)+4,(5*y)+3) == Color.WHITE.getRGB() )
            maze.getMaze()[x][y].setE(false);
        else
            maze.getMaze()[x][y].setE(true);
    }
    private void getWWall(BufferedImage res, int x, int y){
        if (    res.getRGB(5*x, (5*y)+1) == Color.WHITE.getRGB() &&
                res.getRGB(5*x,(5*y)+2) == Color.WHITE.getRGB() &&
                res.getRGB(5*x,(5*y)+3) == Color.WHITE.getRGB() )
            maze.getMaze()[x][y].setW(false);
        else
            maze.getMaze()[x][y].setW(true);
    }

    /**
     * Metoda magiczna - w przypadku niewłaściwego formatu, wykorzystuje wymiary i funkcję skrótu obrazka do wygenerowania nowego labiryntu.
     * @param bi Odczytany obrazek
     */
    private void magic(BufferedImage bi) {
        System.out.println("Niewłaściwy format obrazka.");
        System.out.println("Pora na magię!");
        //magia
        Backtracker BT = new Backtracker(bi.hashCode());
        Maze magicMaze = new Maze(bi.getHeight() / 5, bi.getWidth() / 5 , 3, bi.hashCode() , "Backtracker");
        BT.generator(magicMaze, 0, 0);
        this.maze = magicMaze;
    }


    /**
     * Sprawdza czy komórka ma odpowiednie rogi.
     * @param bi Odczytywany obrazek
     * @param x  Pierwsza współrzędna
     * @param y  Druga współrzędna
     * @return Fałsz jeśli co najmniej jeden róg jest biały.
     */
    private boolean checkCorners(BufferedImage bi, int x, int y) {
        if (bi.getRGB(5 * x, 5 * y) == Color.WHITE.getRGB() ||
                bi.getRGB(5 * x + 4, 5 * y) == Color.WHITE.getRGB() ||
                bi.getRGB(5 * x, 5 * y + 4) == Color.WHITE.getRGB() ||
                bi.getRGB(5 * x + 4, 5 * y + 4) == Color.WHITE.getRGB())
            return false;
        else
            return true;
    }

    /**
     * Konstruktor, odczytuje obrazek. Przy poprawnych wymiarach podejmuje próbę dekodowania, przy niepoprawnych - czaruje.
     * @param file plik z obrazkiem
     * @throws IOException przy błędzie odczytywania
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

    private Maze decodeBMPToMaze(BufferedImage bi) {
        int length = bi.getHeight() / 5;
        int width = bi.getWidth() / 5;
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
