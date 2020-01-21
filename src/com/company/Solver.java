package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * Klasa odpowaidająca za wygenerowanie labiryntu z zaznaczoną ścieżką
 */
public class Solver {
    private Maze maze;
    private String fileName;
    private int[][] mazePath;
    private int[] start={0,0};
    private int[] end={0,0};
    private Stack<Cell> stack;
    public Solver(File file) throws IOException {
        BmpToMaze MazeBuff=new BmpToMaze(file);
        maze=MazeBuff.getMaze();
        end[0]=maze.getWidth()-1;
        end[1]=maze.getLength()-1;
        mazePath=new int[maze.getWidth()][maze.getLength()];
        for(int i=0;i<maze.getWidth();i++){
            for(int j=0;j<maze.getLength();j++){
                mazePath[i][j]=-1;
            }
        }
        solve();
    }
    private void solve() {
        stack=new Stack<Cell>();
        Cell tmp1 = null, tmp2;
        int x, y;
        maze.visitedReset();
        maze.getMaze()[start[0]][start[1]].setVisited(true);
        mazePath[start[0]][start[1]] = 0;
        stack.push(maze.getMaze()[start[0]][start[1]]);
        while ((!stack.empty())&&stack.firstElement().getX() != end[0] && stack.firstElement().getY() != end[1]) {
            tmp1 = stack.pop();
            x = tmp1.getX();
            y = tmp1.getY();
            if (!tmp1.isN()&&y<maze.getLength()-1) {
                tmp2 = maze.getMaze()[x][y + 1];
                if (!tmp2.isVisited()) {
                    mazePath[tmp2.getX()][tmp2.getY()] = mazePath[x][y] + 1;
                    tmp2.setVisited(true);
                    stack.push(tmp2);
                }
            }
            if (!tmp1.isS()&&y>0) {
                tmp2 = maze.getMaze()[x][y - 1];
                if (!tmp2.isVisited()) {
                    mazePath[tmp2.getX()][tmp2.getY()] = mazePath[x][y] + 1;
                    tmp2.setVisited(true);
                    stack.push(tmp2);
                }
            }
            if (!tmp1.isE()&&x<maze.getWidth()-1) {
                tmp2 = maze.getMaze()[x + 1][y];
                if (!tmp2.isVisited()) {
                    mazePath[tmp2.getX()][tmp2.getY()] = mazePath[x][y] + 1;
                    tmp2.setVisited(true);
                    stack.push(tmp2);
                }
            }
            if (!tmp1.isW()&&x>0) {
                tmp2 = maze.getMaze()[x - 1][y];
                if (!tmp2.isVisited()) {
                    mazePath[tmp2.getX()][tmp2.getY()] = mazePath[x][y] + 1;
                    tmp2.setVisited(true);
                    stack.push(tmp2);
                }
            }
        }
        maze.visitedReset();
        if(!stack.empty())
            tmp1 = stack.pop();
        x = tmp1.getX();
        y = tmp1.getY();
        int i = mazePath[x][y];
        maze.getMaze()[x][y].setVisited(true);
        while (i != 0) {
            if(y>=0&&y<maze.getLength()) {
                if ((!maze.getMaze()[x][y].isN()) && (i - mazePath[x][y + 1] == 1)) {
                    maze.getMaze()[x][y + 1].setVisited(true);
                    y = y + 1;
                    i--;
                }
                if ((!maze.getMaze()[x][y].isS()) && (i - mazePath[x][y - 1] == 1)) {
                    maze.getMaze()[x][y - 1].setVisited(true);
                    y = y - 1;
                    i--;
                }
            }
            if(x>=0&&x<maze.getWidth()) {
                if ((!maze.getMaze()[x][y].isW()) && (i - mazePath[x - 1][y] == 1)) {
                    maze.getMaze()[x - 1][y].setVisited(true);
                    x = x - 1;
                    i--;
                }
                if ((!maze.getMaze()[x][y].isE()) && (i - mazePath[x + 1][y] == 1)) {
                    maze.getMaze()[x + 1][y].setVisited(true);
                    x = x + 1;
                    i--;
                }
            }
        }
    }
    public Maze getSolvedMaze(){
        return maze;
    }
}
