package com.company;

import java.util.Stack;

public class Backtracker extends Algo {

    private Stack<Cell> stack;

    public Backtracker(long seed)
    {
        super(seed);
        stack = new Stack<Cell>();
    }




    public void generator(Maze maze, int x, int y )
    {

        Cell tmp;
        maze.getMaze()[x][y].setVisited(true);
        stack.push(maze.getMaze()[x][y]);
        boolean check = true;
        boolean N,S,E,W;
        while(!stack.isEmpty())
        {
            check = true;
            tmp = stack.pop();
            x = tmp.getX();
            y = tmp.getY();
            N=S=E=W=false;

            while(check) {

                if(N && S && E && W)
                    break;

            switch(randomGenerator()) {
                case 0:
                    N=true;
                    if(y >= maze.getLength()-1)
                        break;
                    if (maze.getMaze()[x][y + 1].isVisited()) {
                        break;
                    } else {
                        stack.push(maze.getMaze()[x][y]);
                        maze.getMaze()[x][y + 1].setVisited(true);
                        stack.push(maze.getMaze()[x][y + 1]);
                        maze.getMaze()[x][y].setN(false);
                        maze.getMaze()[x][y + 1].setS(false);

                        y++;
                        check = false;
                        break;
                    }

                case 1:
                    S=true;
                    if(y <=0)
                        break;
                    if (maze.getMaze()[x][y - 1].isVisited()) {

                        break;
                    } else {
                        stack.push(maze.getMaze()[x][y]);
                        maze.getMaze()[x][y - 1].setVisited(true);
                        stack.push(maze.getMaze()[x][y - 1]);
                        maze.getMaze()[x][y].setS(false);
                        maze.getMaze()[x][y - 1].setN(false);

                        y--;
                        check = false;
                        break;
                    }
                case 2:
                    E=true;
                    if(x >= maze.getWidth()-1)
                        break;
                    if (maze.getMaze()[x+1][y].isVisited()) {

                        break;
                    } else {
                        stack.push(maze.getMaze()[x][y]);
                        maze.getMaze()[x + 1][y].setVisited(true);
                        stack.push(maze.getMaze()[x + 1][y]);
                        maze.getMaze()[x][y].setE(false);
                        maze.getMaze()[x + 1][y].setW(false);

                        x++;
                        check = false;
                        break;
                    }
                case 3:
                    W=true;
                    if(x <= 0)
                        break;
                    if (maze.getMaze()[x - 1][y].isVisited()) {
                        break;
                    } else {
                        stack.push(maze.getMaze()[x][y]);
                        maze.getMaze()[x - 1][y].setVisited(true);
                        stack.push(maze.getMaze()[x - 1][y]);
                        maze.getMaze()[x][y].setW(false);
                        maze.getMaze()[x - 1][y].setE(false);

                        x--;
                        check = false;
                        break;
                    }
                default:
                    continue;


            }
            }

        }

    }
}