package com.acme.ttt;

public abstract class Gameplay {
    protected GameEngine engine;
    protected Coordinate nextMove;
    protected Player playerX;
    protected Player playerO;

    public void play() {
        this.engine = this.createGameEngine();
        this.playerX = this.createPlayer(Mark.X);
        this.playerO = this.createPlayer(Mark.O);
        this.nextMove = null;
        if (this.hasWinner()) {
            this.declareWinner();
        } else {
            this.declareDraw();
        }
    }

    protected boolean hasWinner() {
        this.refreshView();
        while (!engine.isGameOver()) {
            this.declareTurn();
            this.makeValidChoice();
            if (this.playNextMove()) {
                return true;
            }
        }
        return false;
    }

    protected boolean playNextMove() {
        Coordinate move = this.nextMove;
        this.nextMove = null;
        boolean result = this.engine.play(move);
        this.refreshView();
        return result;
    }

    protected void makeValidChoice() {
        Board board = this.engine.getBoard();
        Player currentPlayer = this.getCurrentPlayer();
        this.nextMove = currentPlayer.makeChoice(board);
        if (!board.canPlace(this.nextMove.i(), this.nextMove.j())) {
            this.makeValidChoice();
        }
        this.refreshView();
        if (!currentPlayer.confirmChoice(board, this.nextMove)) {
            this.makeValidChoice();
        }
    }

    protected Player getCurrentPlayer() {
        return this.engine.getCurrentMark() == Mark.X ? this.playerX : this.playerO;
    }

    protected abstract GameEngine createGameEngine();

    protected abstract Player createPlayer(Mark mark);

    protected abstract void refreshView();

    protected abstract void declareTurn();

    protected abstract void declareWinner();

    protected abstract void declareDraw();
}
