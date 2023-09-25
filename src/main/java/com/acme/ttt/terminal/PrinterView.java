package com.acme.ttt.terminal;

import com.acme.ttt.Board;
import com.acme.ttt.Coordinate;
import com.acme.ttt.GameEngine;
import com.acme.ttt.GameView;
import com.acme.ttt.Mark;
import com.acme.ttt.i18n.Text;

public class PrinterView implements GameView {

    protected final Text text;
    protected final Printer printer;
    protected final GameEngine engine;

    public PrinterView(Text text, Printer printer, GameEngine engine) {
        this.text = text;
        this.printer = printer;
        this.engine = engine;
    }

    @Override
    public void declareTurn() {
        this.printer.printLine(this.text.playerTurn.formatted(this.engine.getCurrentMark()));
    }

    @Override
    public void declareWinner() {
        this.printer.printLine(this.text.playerWins.formatted(this.engine.getCurrentMark().other()));
    }

    @Override
    public void declareDraw() {
        this.printer.printLine(this.text.boardIsFull);
    }

    @Override
    public void refresh() {
        Board board = this.engine.getBoard();
        int length = board.getLength();
        this.printer.printLine("");
        for (int i = 0; i < length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(" |");
            for (int j = 0; j < length; j++) {
                Coordinate current = new Coordinate(i, j);
                sb.append(this.cellToString(current));
            }
            sb.append("|");
            this.printer.printLine(sb.toString());
        }
    }

    private String cellToString(Coordinate current) {
        Board board = this.engine.getBoard();
        int i = current.i();
        int j = current.j();
        Mark player = board.at(current);
        if (current.equals(this.engine.getNextMove())) {
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
