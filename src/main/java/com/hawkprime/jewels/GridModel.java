package com.hawkprime.jewels;

import com.sun.media.jfxmedia.events.PlayerEvent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridModel {
    private List<List<String>> cells;
    private int width;
    private int height;
    private int jewelCount;
    private Random random;
    private int removedCells;

    Player player = new Player();

    public GridModel(int width, int height, int jewelCount) {
        this.width = width;
        this.height = height;
        this.jewelCount = jewelCount;
        this.random = new Random();

        cells = new ArrayList<>(width);
        for (int x = 0; x < width; ++x) {
            List<String> col = new ArrayList<>(height);
            cells.add(col);
            for (int y = 0; y < height; ++y) {
                col.add(generateCellValue());
            }
        }
    }

    public void setCells(char[] cells) {
        if (cells.length != width * height) {
            throw new IllegalArgumentException("Cell values does not match grid dimensions.");
        }
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                this.cells.get(x).set(y, String.valueOf(cells[y * width + x]));
            }
        }
    }

    public char getCell(int x, int y) {
        return cells.get(x).get(y).charAt(0);
    }

    public char[] getCells() {
        char[] grid = new char[width * height];
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                grid[y * width + x] = cells.get(x).get(y).charAt(0);
            }
        }
        return grid;
    }

    public void swapCell(int sx, int sy, int tx, int ty) {
        String curr = cells.get(tx).get(ty);
        cells.get(tx).set(ty, cells.get(sx).get(sy));
        cells.get(sx).set(sy, curr);
    }

    public List<Point> checkAdjacentCells(int x, int y) {
        List<Point> finalList = new ArrayList<>();
        List<Point> matching = new ArrayList<>();
        String ch = cells.get(x).get(y);

        // check up
        for (int i = y - 1; i >= 0; --i) {
            if (cells.get(x).get(i).equals(ch)) {
                matching.add(new Point(x, i));
            } else {
                break;
            }
        }

        // check Down
        for (int i = y + 1; i < height; ++i) {
            if (cells.get(x).get(i).equals(ch)) {
                matching.add(new Point(x, i));
            } else {
                break;
            }
        }

        // If we've got enough up down, add them
        if (matching.size() >= 2) {
            finalList.addAll(matching);
        }

        // check left
        matching.clear();
        for (int i = x - 1; i >= 0; --i) {
            if (cells.get(i).get(y).equals(ch)) {
                matching.add(new Point(i, y));
            } else {
                break;
            }
        }

        // check right
        for (int i = x + 1; i < width; ++i) {
            if (cells.get(i).get(y).equals(ch)) {
                matching.add(new Point(i, y));
            } else {
                break;
            }
        }

        // if we've got enough left right, add them
        if (matching.size() >= 2) {
            finalList.addAll(matching);
        }

        // if we got something, add the current cell
        if (!finalList.isEmpty()) {
            finalList.add(new Point(x, y));
        }

        return finalList;
    }

    private String generateCellValue() {
        return String.valueOf((char) (random.nextInt(jewelCount + 1)));
    }

    public char[] removeCells(List<Point> cellsToRemove) {
        removedCells = 0;
        cellsToRemove.sort((a, b) -> a.y - b.y);
        for (Point cell : cellsToRemove) {
            List<String> col = cells.get(cell.x);
            col.remove(cell.y);
            col.add(0, generateCellValue());
            removedCells++;
        }
        // if player remove 3 cells add 100point to his score
        if (removedCells == 3){
            player.setScore(100);
        }
        // if he remove more then 3 add 100 points to player and for every other cell add 50 points
        else if (removedCells > 3){
            player.setScore(100);
            for (int i = 3; i < removedCells; i++){
                player.setScore(50);
            }
        }

        return getCells();
    }
}
