package com.acme.ttt.network;

import com.acme.ttt.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;

public class TeeTerminal implements Terminal {

    private final List<Terminal> sinks;
    private final Terminal master;

    public TeeTerminal(Terminal master) {
        this.sinks = new ArrayList<>();
        this.master = master;
        this.sinks.add(master);
    }

    public void append(Terminal terminal) {
        this.sinks.add(terminal);
    }

    @Override
    public String nextLine() {
        return this.master.nextLine();
    }

    @Override
    public void printLine(String message) {
        this.sinks.forEach(t -> t.printLine(message));
    }
}
