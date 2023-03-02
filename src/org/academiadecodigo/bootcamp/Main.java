package org.academiadecodigo.bootcamp;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server(5555);
            server.init();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
