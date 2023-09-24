package com.acme.ttt.network;

import com.acme.ttt.terminal.Terminal;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class NetworkTerminal implements Terminal {

    protected final Scanner scanner;
    protected final PrintStream printer;

    public NetworkTerminal(Socket socket) {
        try {
            this.scanner = new Scanner(socket.getInputStream());
            this.printer = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String nextLine() {
        return this.scanner.nextLine().trim();
    }

    @Override
    public void printLine(String message) {
        this.printer.println(message);
    }
}
