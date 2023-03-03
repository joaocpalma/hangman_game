package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private ServerSocket serverSocket;
    private String hangManWord;
    private String[] gameWords = {
            "hello",
            "blood",
            "message",
            "question",
            "infinite"
    };
    private int random = (int) (Math.random() * gameWords.length);
    HashMap<String, Socket> playerList = new HashMap<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }

    public HashMap<String, Socket> getPlayerList() {
        return playerList;
    }

    public void init() {
        while (true) {
            try {
                hangManWord = gameWords[random];

                System.out.println("Waiting for player");
                Socket client = serverSocket.accept();

                PrintStream printStream = new PrintStream(client.getOutputStream());

                Prompt prompt = new Prompt(client.getInputStream(), printStream);

                FileReader gameMenu = new FileReader("sources/HangMan_menu.txt");

                BufferedReader readMenu = new BufferedReader(gameMenu);


                String menu;

                while ((menu = readMenu.readLine()) != null) {
                    printStream.println(menu);
                    printStream.flush();
                }

                StringInputScanner askName = new StringInputScanner();
                askName.setMessage("What is your name? \n");
                askName.setError("Write you name...");

                String name = prompt.getUserInput(askName);

                printStream.println("Nice to meet you " + name);

                playerList.put(name, client);

                System.out.println(name + " connected");

                Thread thread = new Thread(new ServerHelper(client, name, hangManWord, this));
                thread.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

