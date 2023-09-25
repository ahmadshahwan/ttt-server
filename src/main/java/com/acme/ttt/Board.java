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

    public Mark at(Coordinate coordinate) {
        int i = coordinate.i();
        int j = coordinate.j();
        return this.cells[i][j];
    }

    public boolean isFull() {
        return this.numberOfEmptyCells <= 0;
    }
    public boolean canPlace(Coordinate coordinate) {
        return !this.isFull() && this.at(coordinate) == null;
    }

    public synchronized void place(Coordinate coordinate, Mark mark) {
        if (!canPlace(coordinate)) {
            throw new IllegalStateException("Board is full or cell is occupied.");
        }
        int i = coordinate.i();
        int j = coordinate.j();
        this.cells[i][j] = mark;
        this.numberOfEmptyCells--;
    }

    public int getLength() {
        return this.length;
    }
}
