package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class Server {
    private ServerSocket serverSocket;
    private ServerHelper serverHelper;

    HashMap<String,Socket> playerList = new HashMap<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }

    public HashMap<String, Socket> getPlayerList() {
        return playerList;
    }

    public void init(){
        while (true){
            try {


                System.out.println("Waiting for player");
                Socket client = serverSocket.accept();

                PrintStream printStream = new PrintStream(client.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                Prompt prompt = new Prompt(client.getInputStream(), printStream);

                StringInputScanner askName = new StringInputScanner();
                askName.setMessage("What is your name? \n");
                askName.setError("Write you name...");

                String name = prompt.getUserInput(askName);

                printStream.println("Nice to meet you " + name);

                playerList.put(name,client);

                System.out.println("Player connected");

                Thread thread = new Thread(new ServerHelper(client,this));
                thread.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

