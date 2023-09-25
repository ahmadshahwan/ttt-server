package com.acme.ttt.network;

import com.acme.ttt.terminal.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeePrinter implements Printer {

    private final List<Printer> sinks;

    public TeePrinter(Printer... others) {
        this.sinks = new ArrayList<>();
        this.sinks.addAll(Arrays.asList(others));
    }

    @Override
    public void printLine(String message) {
        this.sinks.forEach(t -> t.printLine(message));
    }
}
