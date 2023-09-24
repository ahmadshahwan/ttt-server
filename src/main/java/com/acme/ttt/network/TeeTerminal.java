package com.acme.ttt.network;

import com.acme.ttt.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;

public class TeeTerminal implements Terminal {

    private List<Terminal> sinks;
    private Terminal master;

    public TeeTerminal(Terminal master) {
        this.sinks = new ArrayList<>();
        this.master = master;
        this.sinks.add(master);
    }
    public TeeTerminal() {
        this.sinks = new ArrayList<>();
    }

    public void append(Terminal terminal) {
        this.sinks.add(terminal);
    }

    @Override
    public String nextLine() {
        return this.master == null ? null : this.master.nextLine();
    }

    @Override
    public void printLine(String message) {
        this.sinks.forEach(t -> t.printLine(message));
    }
}
