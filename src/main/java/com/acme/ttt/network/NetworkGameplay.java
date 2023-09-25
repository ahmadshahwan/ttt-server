package com.acme.ttt.network;

import com.acme.ttt.GameEngine;
import com.acme.ttt.GameView;
import com.acme.ttt.Gameplay;
import com.acme.ttt.Mark;
import com.acme.ttt.Player;
import com.acme.ttt.i18n.Text;
import com.acme.ttt.terminal.Terminal;
import com.acme.ttt.terminal.TerminalPlayer;
import com.acme.ttt.terminal.PrinterView;

public class NetworkGameplay extends Gameplay {
    protected final Terminal terminalX;
    protected final Terminal terminalO;

    protected final Text text;
    protected final int size;
    public NetworkGameplay(Text text, int size, Terminal terminalX, Terminal terminalO) {
        this.text = text;
        this.size = size;
        this.terminalX = terminalX;
        this.terminalO = terminalO;
    }

    @Override
    protected int getBoardDimension() {
        return this.size;
    }

    @Override
    protected GameView createView(GameEngine engine) {
        return new PrinterView(this.text, new TeePrinter(terminalX, terminalO), engine);
    }

    @Override
    protected Player createPlayer(Mark mark) {
        Terminal terminal = mark == Mark.X ? terminalX : terminalO;
        return new TerminalPlayer(terminal, this.text);
    }
}
