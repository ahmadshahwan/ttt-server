package com.acme.ttt.i18n;

public abstract class Text {

    public String playerTurn = "Player %s's turns.";
    public String enterLabel = "Please enter the label of the cell where to place your mark.";
    public String badInput = "Bad input. Please make your choice.";
    public String confirmChoice = "Confirm your choice by pressing Enter.";
    public String playerWins = "Player %s won.";
    public String gameEnded = "Game ended unexpectedly.";
    public String welcomePlayer = "Your are player %s.";
    public String boardIsFull = "Board is full. Game over.";
    public String boardChoice = "Please choose board dimension.";

    public String awaitingPlayer = "Awaiting player %s to connect.";

    public String badBoardChoice = "Board dimension should be between two and nine.";


    public static EnText en() {
        return new EnText();
    }

    public static FrText fr() {
        return new FrText();
    }
}
