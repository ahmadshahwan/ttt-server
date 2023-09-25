package com.acme.ttt.terminal;

import com.acme.ttt.Board;
import com.acme.ttt.Coordinate;
import com.acme.ttt.Player;
import com.acme.ttt.i18n.Text;

public class TerminalPlayer implements Player {

    protected final Terminal terminal;
    protected final Text text;
    protected Coordinate lastRead;

    public TerminalPlayer(Terminal terminal, Text text) {
        this.terminal = terminal;
        this.text = text;
    }

    @Override
    public Coordinate makeChoice(Board board) {
        if (lastRead != null) {
            Coordinate result = this.lastRead;
            this.lastRead = null;
            return result;
        }
        this.terminal.printLine(text.enterLabel);
        Coordinate next = this.tryToMakeChoice(board);
        if (next != null) {
            return next;
        } else {
            this.terminal.printLine(this.text.badInput);
            return this.makeChoice(board);
        }
    }

    public Coordinate tryToMakeChoice(Board board) {
        String line = this.terminal.nextLine();
        return this.tryToMakeChoice(board, line);
    }

    public Coordinate tryToMakeChoice(Board board, String line) {
        int length = board.getLength();
        int cell;
        try {
            cell = Integer.parseInt(line);
        } catch (NumberFormatException ignore) {
            return null;
        }
        if (cell > length * length) {
            return null;
        }
        int i = (cell - 1) / length;
        int j = (cell - 1) % length;
        if (!board.canPlace(new Coordinate(i, j))) {
            return null;
        }
        return new Coordinate(i, j);
    }

    @Override
    public boolean confirmChoice(Board board, Coordinate coordinate) {
        this.terminal.printLine(this.text.confirmChoice);
        String line = this.terminal.nextLine();
        if (line.isEmpty()) {
            return true;
        }
        Coordinate attempt = this.tryToMakeChoice(board, line);
        if (coordinate.equals(attempt)) {
            return true;
        }
        if (attempt != null) {
            this.lastRead = attempt;
        }
        return false;
    }
}
