package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Interface {

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }


    public void Menu() throws IOException {

        Scanner scan = new Scanner(System.in);
        String choice = null;
        do {
            System.out.println("##########   Menu:   ##########");
            System.out.println("Command Options: ");
            System.out.println("1: Generate a maze (Reverse backtracker)");
            System.out.println("2: Generate a maze (Eller algorith)");
            System.out.println("3: Solve a maze ");
            System.out.println("q: Quit");


            choice = scan.nextLine();
            switch (choice){
                case "1":
                    BacktrackerMenu();
                    break;

                case "2":
                    EllerMenu();
                    break;

                case "3":
                    SolverMenu();
                    break;

                case "q":
                    System.out.println("Dzieki ze wpadles mordeczko. TRZYMAJ SIE NA TEJ ELCE! <3");
                    scan.nextByte();
                    break;

            }
        }while (!choice.equals("q"));
    }

    public void BacktrackerMenu() throws IOException {
        Scanner scan = new Scanner(System.in);
        int width,length,lvl;
        String name;

        clearConsole();
        System.out.println("Podaj szerokosc: ");
        width = scan.nextInt();
        while(width<0) {
            System.out.println("Blad danych, podaj jeszcze raz; ");
            width = scan.nextInt();
        }
        clearConsole();

        System.out.println("Podaj wysokosc: ");
        length = scan.nextInt();
        while(length<0) {
            System.out.println("Blad danych, podaj jeszcze raz; ");
            length = scan.nextInt();
        }
        clearConsole();

        System.out.println("Podaj poziom trudnosci (1, 2 lub 3!!): ");
        lvl = scan.nextInt();
        while(lvl<1 || lvl>3) {
            System.out.println("Blad danych, podaj jeszcze raz; ");
            lvl = scan.nextInt();
        }

        System.out.println("Podaj nazwe swojego labiryntu:  ");
        name = scan.nextLine();
        name = scan.nextLine();
        File tmp = new File(name);
        /**Sprawdzam czy istnieje labirynt o podanej nazwie**//*
        while(tmp.exists()) {
            System.out.println("Taki labirynt juz istnieje, podaj nowa nazwe: ");
            name = scan.nextLine();
        }
*/
        Backtracker BT = new Backtracker(10);
        Maze maze = new Maze(length, width, lvl, 10 /**Narazie na stale**/, "Backtracker"); /** Trzeba rozkminic jak tutaj wrzucac seed**/

        BT.generator(maze,0,0);
        new MazeToBmp(maze,name,false);
        File tmp2 = new File(name + ".bmp");
        BmpToMaze btm = new BmpToMaze(tmp2);
        new MazeToBmp(btm.getMaze(),"abcd.bmp",false);
        scan.nextLine();
        /**Dalej modul odpowiedzialny za zapis uzywajac nazwy**/

    }

    public void EllerMenu() throws IOException {

        Scanner scan = new Scanner(System.in);
        int width,length,lvl;
        String name;

        clearConsole();
        System.out.println("Podaj szerokosc: ");
        width = scan.nextInt();
        clearConsole();

        while(width<0) {
            System.out.println("Blad danych, podaj jeszcze raz; ");
            width = scan.nextInt();
        }
        clearConsole();

        System.out.println("Podaj wysokosc: ");
        length = scan.nextInt();

        clearConsole();
        while(length<0) {
            System.out.println("Blad danych, podaj jeszcze raz; ");
            length = scan.nextInt();
        }
        clearConsole();

        System.out.println("Podaj poziom trudnosci (1, 2 lub 3!!): ");
        lvl = scan.nextInt();

        clearConsole();
        while(lvl<1 || lvl>3) {
            System.out.println("Blad danych, podaj jeszcze raz; ");
            lvl = scan.nextInt();
        }

        clearConsole();
        System.out.println("Podaj nazwe swojego labiryntu:  ");
        name = scan.nextLine();
        name = scan.nextLine();
        File tmp = new File(name);

        clearConsole();
        /**Sprawdzam czy istnieje labirynt o podanej nazwie**/
        while(tmp.exists()) {
            System.out.println("Taki labirynt juz istnieje, podaj nowa nazwe: ");
            name = scan.nextLine();
        }
        clearConsole();

        Maze maze = new Maze(length, width, lvl, 10, "Eller");


        /**Dalej modul odpowiedzialny za zapis i za generacje labiryntu**/
        Eller EL = new Eller(123);

        EL.generator(maze);
        new MazeToBmp(maze,name,false);
        File tmp2 = new File(name + ".bmp");
        BmpToMaze btm = new BmpToMaze(tmp2);
        new MazeToBmp(btm.getMaze(),"abcd.bmp",false);
        scan.nextLine();



    }

    public void SolverMenu() throws IOException {

        Scanner scan = new Scanner(System.in);
        String name;

        clearConsole();
        System.out.println("Podaj nazwe pliku z labiryntem: ");
        name = scan.nextLine();

        File tmp = new File(name + ".bmp");
        clearConsole();

        /**Sprawdzam czy istnieje labirynt o podanej nazwie**/
        /*while(!tmp.exists()) {
            System.out.println("Taki labirynt nie istnieje, podaj nowa nazwe: ");
            name = scan.nextLine();
        }*/

        /**Dalej modul odpowiedzialny za solver**/
        Solver solver=new Solver(tmp);
        Maze maze=solver.getSolvedMaze(); 
        new MazeToBmp(maze,name + "_solved", true);

    }

    public static void main(String[] args) throws IOException {

        Interface inter = new Interface();
        inter.Menu();
    }
}
