package com.acme.ttt.network;

import com.acme.ttt.GameEngine;
import com.acme.ttt.Mark;
import com.acme.ttt.Player;
import com.acme.ttt.i18n.Text;
import com.acme.ttt.local.LocalTerminal;
import com.acme.ttt.terminal.Terminal;
import com.acme.ttt.terminal.TerminalGameplay;
import com.acme.ttt.terminal.TerminalPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkGameplay extends TerminalGameplay implements AutoCloseable {
    public static int PORT = 8082;
    protected ServerSocket server;
    protected List<Socket> clients = new ArrayList<>(2);
    public NetworkGameplay(Text text) {
        super(new TeeTerminal(new LocalTerminal()), text);
    }

    public NetworkGameplay() {
        this(Text.en());
    }

    @Override
    protected GameEngine createGameEngine() {
        try {
            this.server = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return super.createGameEngine();
    }

    @Override
    protected Player createPlayer(Mark mark) {
        try {
            this.terminal.printLine(this.text.awaitingPlayer.formatted(mark));
            Socket client = this.server.accept();
            this.clients.add(client);
            Terminal terminal = new NetworkTerminal(client);
            terminal.printLine(text.welcomePlayer.formatted(mark));
            if (this.terminal instanceof TeeTerminal tee) {
                tee.append(terminal);
            }
            return new TerminalPlayer(terminal, this.text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.server != null) {
            this.server.close();
        }
        for (Socket client : this.clients) {
            client.close();
        }
    }

    public static void main(String[] args) {
        try (NetworkGameplay gameplay = new NetworkGameplay()) {
            gameplay.play();
        } catch (Exception e) {
            System.err.println(Text.en().gameEnded);
        }
    }
}
