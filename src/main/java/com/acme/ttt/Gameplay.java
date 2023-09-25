package com.acme.ttt;

public abstract class Gameplay implements Runnable {
    protected GameEngine engine;
    protected GameView view;
    protected Player playerX;
    protected Player playerO;

    public void run() {
        int size = this.getBoardDimension();
        this.engine = new GameEngine(size, Mark.X);
        this.view = this.createView(this.engine);
        this.playerX = this.createPlayer(Mark.X);
        this.playerO = this.createPlayer(Mark.O);
        if (this.hasWinner()) {
            this.view.declareWinner();
        } else {
            this.view.declareDraw();
        }
    }

    protected boolean hasWinner() {
        this.view.refresh();
        while (!engine.isGameOver()) {
            this.view.declareTurn();
            this.makeValidChoice();
            if (this.playNextMove()) {
                return true;
            }
        }
        return false;
    }

    protected boolean playNextMove() {
        boolean result = this.engine.play();
        this.view.refresh();
        return result;
    }

    protected void makeValidChoice() {
        Board board = this.engine.getBoard();
        Player currentPlayer = this.getCurrentPlayer();
        Coordinate nextMove = currentPlayer.makeChoice(board);
        if (!board.canPlace(nextMove)) {
            this.makeValidChoice();
            return;
        } else {
            this.engine.setNextMove(nextMove);
        }
        this.view.refresh();
        if (!currentPlayer.confirmChoice(board, nextMove)) {
            this.makeValidChoice();
        }
    }

    protected Player getCurrentPlayer() {
        return this.engine.getCurrentMark() == Mark.X ? this.playerX : this.playerO;
    }

    protected abstract int getBoardDimension();

    protected abstract GameView createView(GameEngine engine);

    protected abstract Player createPlayer(Mark mark);
}
