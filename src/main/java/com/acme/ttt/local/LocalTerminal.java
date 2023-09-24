package com.acme.ttt.local;

import com.acme.ttt.terminal.Terminal;

import java.util.Scanner;

public class LocalTerminal implements Terminal {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() {
        return this.scanner.nextLine().trim();
    }

    @Override
    public void printLine(String message) {
        System.out.println(message);
    }
}
