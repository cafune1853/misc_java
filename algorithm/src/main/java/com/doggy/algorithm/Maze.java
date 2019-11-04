package com.doggy.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Maze {
    private int row;
    private int column;
    private Point[][] maze;

    public Maze(int row, int column, int[][] maze) {
        this.row = row;
        this.column = column;
        this.maze = new Point[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Point point = new Point(i, j, maze[i][j]);
                this.maze[i][j] = point;
            }
        }
    }

    public Point getPoint(int x, int y){
        return maze[x][y];
    }

    public boolean findPath(Point start, Point end, List<Point> path){
        path.add(start);
        if(start.equals(end)){
            return true;
        }
        start.state = -1;
        Point up = start.getUpPointInMaze(this);
        if(up != null && up.state == 1 && findPath(up, end, path)){
            return true;
        }
        Point right = start.getRightPointInMaze(this);
        if(right != null && right.state == 1 && findPath(right, end, path)){
            return true;
        }
        Point down = start.getDownPointInMaze(this);
        if(down != null && down.state == 1 && findPath(down, end, path)){
            return true;
        }
        Point left = start.getLeftPointInMaze(this);
        if(left != null && left.state == 1 && findPath(left, end, path)){
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        Maze maze = new Maze(3, 3, new int[][]{{1,1,1}, {0,1,0},{1, 0, 1}});
        List<Point> path = new ArrayList<>();
        boolean found = maze.findPath(maze.getPoint(0, 0), maze.getPoint(2, 2), path);
        if(found){
            System.out.println(path);
        }else{
            System.out.println("Unreachable");
        }

    }

    private static class Point{
        private final int x;
        private final int y;
        private int state;

        public Point(int x, int y, int state) {
            this.x = x;
            this.y = y;
            this.state = state;
        }

        public Point getLeftPointInMaze(Maze maze){
            if (y - 1 >= 0){
                return maze.maze[x][y - 1];
            }
            return null;
        }

        public Point getRightPointInMaze(Maze maze){
            if (y + 1 < maze.column){
                return maze.maze[x][y + 1];
            }
            return null;
        }

        public Point getUpPointInMaze(Maze maze){
            if (x - 1 >= 0){
                return maze.maze[x - 1][y];
            }
            return null;
        }

        public Point getDownPointInMaze(Maze maze){
            if (x + 1< maze.row){
                return maze.maze[x + 1][y];
            }
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}


