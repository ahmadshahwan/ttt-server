package com.acme.ttt.terminal;

import com.acme.ttt.GameEngine;
import com.acme.ttt.GameView;
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
    protected GameEngine createEngine() {
        this.terminal.printLine(this.text.boardChoice);
        String line = this.terminal.nextLine();
        try {
            int size = Integer.parseInt(line);
            if (size < 3 || size > 9) {
                this.terminal.printLine(this.text.badBoardChoice);
                return this.createEngine();
            }
            return new GameEngine(size, Mark.X);
        } catch (NumberFormatException e) {
            this.terminal.printLine(this.text.badInput);
            return this.createEngine();
        }
    }

    @Override
    protected GameView createView(GameEngine engine) {
        return new TerminalView(this.text, this.terminal, engine);
    }

    @Override
    protected Player createPlayer(Mark mark) {
        return new TerminalPlayer(this.terminal, this.text);
    }
}
