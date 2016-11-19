package com.javarush.test.level30.lesson15.big01.client;

import com.javarush.test.level30.lesson15.big01.Connection;
import com.javarush.test.level30.lesson15.big01.ConsoleHelper;
import com.javarush.test.level30.lesson15.big01.Message;
import com.javarush.test.level30.lesson15.big01.MessageType;

import java.io.IOException;

/**
 * Created by root on 11/19/2016.
 */
public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    //14
    public void run() {
        //14.1.1
        SocketThread socketThread = getSocketThread();
        //14.1.2
        socketThread.setDaemon(true);
        //14.1.3
        socketThread.start();
        //14.1.4
        synchronized (this){
            try {
                this.wait();
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Error in socketThread");
                return;
            }
        }
        //14.1.5
        if (clientConnected){
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
            while (true){
                String reader = ConsoleHelper.readString();
                //14.1.7
                if ("exit".equals(reader)) break;
                if (shouldSentTextFromConsole()){
                    sendTextMessage(reader);
                }
            }
        } else ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
        //14.1.6

    }

    public class SocketThread extends Thread{

    }

    protected String getServerAddress(){
        ConsoleHelper.writeMessage("Input server IP:");
        String IP = ConsoleHelper.readString();
        return IP;
    }
    protected int getServerPort(){
        ConsoleHelper.writeMessage("Input server port:");
        int serverPort = ConsoleHelper.readInt();
        return serverPort;
    }

    protected String getUserName(){
        ConsoleHelper.writeMessage("Input user name: ");
        String userName = ConsoleHelper.readString();
        return userName;
    }

    protected boolean shouldSentTextFromConsole(){
        return true;
    }

    protected SocketThread getSocketThread(){
        return new SocketThread();
    }

    protected void sendTextMessage(String text){
        try {
            connection.send(new Message(MessageType.TEXT,text));
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Can not send message, connection problem");
            clientConnected = false;
        }
    }


}
