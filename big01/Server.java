package com.javarush.test.level30.lesson15.big01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 11/18/2016.
 */
public class Server {
    private static class Handler extends Thread{

        private Socket socket;

        public Handler(Socket socket){
            this.socket = socket;
        }
    }

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Input server socket number:");
        try (ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())){
                ConsoleHelper.writeMessage("Server running");
            while (true){
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
