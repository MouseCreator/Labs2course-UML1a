package com.example.lab23a.Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private Socket socket;
    
    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            close();
        }
    }

    @Override
    public void run() {
        String message;
        while (socket.isConnected()) {
            try {
                message = bufferedReader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            socket.close();
            bufferedWriter.close();
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
