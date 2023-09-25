package com.acme.ttt.terminal;

import com.acme.ttt.Board;
import com.acme.ttt.Coordinate;
import com.acme.ttt.GameEngine;
import com.acme.ttt.Gameplay;
import com.acme.ttt.Mark;
import com.acme.ttt.Player;
import com.acme.ttt.i18n.Text;

public class TerminalGameplay extends Gameplay {

    protected final Text text;
    protected final Terminal terminal;

    public TerminalGameplay(Terminal terminal, Text text) {
        this.terminal = terminal;
        this.text = text;
    }

    @Override
    protected GameEngine createGameEngine() {
        this.terminal.printLine(this.text.boardChoice);
        String line = this.terminal.nextLine();
        try {
            int size = Integer.parseInt(line);
            if (size < 3 || size > 9) {
                this.terminal.printLine(this.text.badBoardChoice);
                return this.createGameEngine();
            }
            return new GameEngine(size, Mark.X);
        } catch (NumberFormatException e) {
            this.terminal.printLine(this.text.badInput);
            return this.createGameEngine();
        }
    }

    @Override
    protected Player createPlayer(Mark mark) {
        return new TerminalPlayer(this.terminal, this.text);
    }

    @Override
    protected void declareTurn() {
        this.terminal.printLine(this.text.playerTurn.formatted(this.engine.getCurrentMark()));
    }

    @Override
    protected void declareWinner() {
        this.terminal.printLine(this.text.playerWins.formatted(this.engine.getCurrentMark().other()));
    }

    @Override
    protected void declareDraw() {
        this.terminal.printLine(this.text.boardIsFull);
    }

    @Override
    protected void refreshView() {
        Board board = this.engine.getBoard();
        int length = board.getLength();
        this.terminal.printLine("");
        for (int i = 0; i < length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(" |");
            for (int j = 0; j < length; j++) {
                Coordinate current = new Coordinate(i, j);
                sb.append(this.cellToString(current));
            }
            sb.append("|");
            this.terminal.printLine(sb.toString());
        }
    }

    private String cellToString(Coordinate current) {
        Board board = this.engine.getBoard();
        int i = current.i();
        int j = current.j();
        Mark player = board.at(current);
        if (current.equals(this.nextMove)) {
            return " >%s<".formatted(this.engine.getCurrentMark());
        } else if (player == null) {
            int len = board.getLength();
            int label = i * len + j + 1;
            return " %2d ".formatted(label);
        } else if (this.isInWinningCombination(current)) {
            return " [%s]".formatted(player);
        } else {
            return "  %s ".formatted(player);
        }
    }

    private boolean isInWinningCombination(Coordinate current) {
        return this.engine.isInWinningCombination(current);
    }
}
