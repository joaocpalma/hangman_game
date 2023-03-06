package org.academiadecodigo.bootcamp;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        try {
            FileReader gameMenu = new FileReader("/Users/codecadet/workspace/hangman-project/resources/HangMan_menu.txt");
            BufferedReader readMenu = new BufferedReader(gameMenu);

            String menu; //

            // Here the server will print the game menu with game title and rules
            while ((menu = readMenu.readLine()) != null) {
                System.out.println(menu);
            }

            int port = Integer.parseInt(args[0]);
            int playersNum = Integer.parseInt(args[1]);

            Server server = new Server(port, playersNum);
            server.init();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
