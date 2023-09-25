package com.acme.ttt.network;

import com.acme.ttt.Mark;
import com.acme.ttt.i18n.Text;
import com.acme.ttt.terminal.Terminal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer implements Runnable, AutoCloseable {

    public static final int SIZE = 3;
    public static final int NUMBER_OF_THREADS = 9;
    final private Text text;

    public static final int PORT = 8082;
    protected final List<Socket> clients = new ArrayList<>();

    public GameServer(Text text) {
        this.text = text;
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        try(
                ServerSocket server = new ServerSocket(PORT)
        ) {
            while (true) executor.submit(this.acceptPlayers(server));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private NetworkGameplay acceptPlayers(ServerSocket server) {
        try {
            Socket client = server.accept();
            this.clients.add(client);
            Terminal terminalX = new NetworkTerminal(client);
            terminalX.printLine(text.welcomePlayer.formatted(Mark.X));
            terminalX.printLine(text.awaitingPlayer.formatted(Mark.O));
            client = server.accept();
            this.clients.add(client);
            Terminal terminalO = new NetworkTerminal(client);
            terminalO.printLine(text.welcomePlayer.formatted(Mark.O));
            return new NetworkGameplay(this.text, SIZE, terminalX, terminalO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        for (Socket client : this.clients) {
            client.close();
        }
    }

    public static void main(String[] args) {
        Text text = Text.en();
        try (GameServer gameplay = new GameServer(text)) {
            gameplay.run();
        } catch (Exception e) {
            System.err.println(text.gameEnded);
        }
    }
}
