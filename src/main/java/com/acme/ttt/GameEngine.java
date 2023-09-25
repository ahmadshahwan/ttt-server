package com.acme.ttt;

import java.util.Arrays;
import java.util.function.Function;

public class GameEngine {

    private final Board board;
    private final int length;
    private Mark currentMark;
    private boolean gameWon = false;
    private Coordinate[] winningCombination;

    protected Coordinate nextMove;


    public GameEngine(int length, Mark firstPlayer) {
        this.length = length;
        this.board = new Board(length);
        this.currentMark = firstPlayer;
    }

    public Mark getCurrentMark() {
        return currentMark;
    }

    public boolean play() {
        if (this.nextMove == null) {
            throw new IllegalStateException("Next move undefined yet");
        }
        Coordinate coordinate = this.nextMove;
        this.nextMove = null;
        this.board.place(coordinate, this.currentMark);
        this.gameWon = this.checkWin(coordinate);
        this.currentMark = this.currentMark.other();
        return this.gameWon;
    }

    public boolean isGameOver() {
        return this.board.isFull() || this.gameWon;
    }

    public boolean isDraw() {
        return this.board.isFull() && !this.gameWon;
    }

    public boolean isInWinningCombination(Coordinate coordinate) {
        return this.winningCombination != null && Arrays.asList(this.winningCombination).contains(coordinate);
    }

    public Board getBoard() {
        return board;
    }

    public Coordinate getNextMove() {
        return nextMove;
    }

    public void setNextMove(Coordinate nextMove) {
        this.nextMove = nextMove;
    }

    private boolean checkWin(Coordinate coordinate) {
        int i = coordinate.i();
        int j = coordinate.j();
        return this.testLine(i) || this.testColumn(j) || this.testDiagonal(i, j) || this.testAntidiagonal(i, j);
    }


    private boolean testOverRange(Function<Integer, Coordinate> function) {
        Coordinate[] winning = new Coordinate[this.length];
        for (int step = 0; step < this.length; step++) {
            Coordinate coordinate = function.apply(step);
            if (this.board.at(coordinate) != this.currentMark) {
                return false;
            }
            winning[step] = coordinate;
        }
        this.winningCombination = winning;
        return true;
    }

    private boolean testLine(int i) {
        return this.testOverRange(step -> new Coordinate(i, step));
    }

    private boolean testColumn(int j) {
        return this.testOverRange(step -> new Coordinate(step, j));
    }

    private boolean testDiagonal(int i, int j) {
        if (i != j) {
            return false;
        }
        return this.testOverRange(step -> new Coordinate(step, step));
    }

    private boolean testAntidiagonal(int i, int j) {
        if (i != this.length - j - 1) {
            return false;
        }
        return this.testOverRange(step -> new Coordinate(step, this.length - step - 1));
    }
}
