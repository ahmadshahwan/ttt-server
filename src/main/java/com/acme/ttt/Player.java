package com.acme.ttt;

public interface Player {

    Coordinate makeChoice(Board board);

    boolean confirmChoice(Board board, Coordinate coordinate);
}
