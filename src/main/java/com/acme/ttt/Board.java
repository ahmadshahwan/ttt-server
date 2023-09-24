package com.acme.ttt;

public class Board {
    private final Mark[][] cells;
    private final int length;
    private int numberOfEmptyCells;


    public Board(int length) {
        this.length = length;
        this.cells = new Mark[length][length];
        this.numberOfEmptyCells = length * length;
    }

    public Mark at(int i, int j) {
        return this.cells[i][j];
    }

    public boolean isFull() {
        return this.numberOfEmptyCells <= 0;
    }
    public boolean canPlace(int i, int j) {
        return !this.isFull() && this.at(i, j) == null;
    }

    public synchronized void place(int i, int j, Mark mark) {
        if (!canPlace(i, j)) {
            throw new IllegalStateException("Board is full or cell is occupied.");
        }
        this.cells[i][j] = mark;
        this.numberOfEmptyCells--;
    }

    public int getLength() {
        return this.length;
    }
}
