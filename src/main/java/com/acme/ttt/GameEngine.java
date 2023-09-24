package com.acme.ttt;

public class GameEngine {

    private final Board board;
    private final int length;
    private Mark currentMark;
    private boolean gameWon = false;


    public GameEngine(int length, Mark firstPlayer) {
        this.length = length;
        this.board = new Board(length);
        this.currentMark = firstPlayer;
    }

    public Mark getCurrentMark() {
        return currentMark;
    }

    public boolean play(Coordinate coordinate) {
        int i = coordinate.i();
        int j = coordinate.j();
        this.board.place(i, j, this.currentMark);
        this.gameWon = this.checkWin(i, j);
        this.currentMark = this.currentMark.other();
        return this.gameWon;
    }

    public boolean isGameOver() {
        return this.board.isFull() || this.gameWon;
    }

    public boolean isDraw() {
        return this.board.isFull() && !this.gameWon;
    }

    public Board getBoard() {
        return board;
    }

    private boolean checkWin(int i, int j) {
        return this.testLine(i) || this.testColumn(j) || this.testDiagonal(i, j) || this.testAntidiagonal(i, j);
    }

    private boolean testLine(int i) {
        for (int step = 0; step < this.length; step++) {
            if (this.board.at(i, step) != this.currentMark) {
                return false;
            }
        }
        return true;
    }

    private boolean testColumn(int j) {
        for (int step = 0; step < this.length; step++) {
            if (this.board.at(step, j) != this.currentMark) {
                return false;
            }
        }
        return true;
    }

    private boolean testDiagonal(int i, int j) {
        if (i != j) {
            return false;
        }
        for (int step = 0; step < this.length; step++) {
            if (this.board.at(step, step) != this.currentMark) {
                return false;
            }
        }
        return true;
    }

    private boolean testAntidiagonal(int i, int j) {
        if (i != this.length - j - 1) {
            return false;
        }
        for (int step = 0; step < this.length; step++) {
            if (this.board.at(step, this.length - step - 1) != this.currentMark) {
                return false;
            }
        }
        return true;
    }
}
