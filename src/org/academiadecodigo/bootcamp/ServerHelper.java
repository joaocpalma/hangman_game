package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ServerHelper implements Runnable {

    private Socket client;

    private Server server;

    private String handManWord = "Hello";
    private String[] wordSplit = handManWord.split("");

    public ServerHelper(Socket client, Server server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {

                PrintStream localPrintStream = new PrintStream(client.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                Prompt prompt = new Prompt(client.getInputStream(), localPrintStream);

                for (String n : server.getPlayerList().keySet()) {
                    System.out.println(n);
                }

                StringInputScanner askWord = new StringInputScanner();
                askWord.setMessage("Guess Word \n");
                askWord.setError("Not a word...");

                String word = prompt.getUserInput(askWord);

                if (handManWord.equals(word)) {
                    localPrintStream.println("Nice job. The word was " + handManWord);
                    localPrintStream.close();
                    client.close();
                } else {
                    localPrintStream.println("Try again");

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}