package com.example.lab23a.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                ClientHandler handler = new ClientHandler(socket);

                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            closeServer();
        }
    }
    public void closeServer() {
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(2233);
            Server server = new Server(socket);
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
