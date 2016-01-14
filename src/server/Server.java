package server;

import java.io.IOException;
public class Server {
    public static void main(String[] args) {
        try {
            Controller c = new Controller(2048);
            
        } catch (IOException e) {
            System.out.println("* Cannot allocate socket, closing...");
        }
    }
}