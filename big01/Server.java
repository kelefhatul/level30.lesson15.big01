package com.javarush.test.level30.lesson15.big01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by root on 11/18/2016.
 */
public class Server {

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message){
        for (Map.Entry<String, Connection> pair : connectionMap.entrySet()
             ) {
            try {
                pair.getValue().send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Can not send a message");
//                e.printStackTrace();
            }
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


    private static class Handler extends Thread{

        private Socket socket;

        public Handler(Socket socket){
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{
            while (true){
                //8.1
                connection.send(new Message(MessageType.NAME_REQUEST));
                //8.2
                Message message = connection.receive();
                //8.3
                if (message.getType() == MessageType.USER_NAME){
                    //8.4
                    if (message.getData() != null && !message.getData().isEmpty()){
                        if (connectionMap.get(message.getData()) == null){
                            //8.5
                            connectionMap.put(message.getData(), connection);
                            //8.6
                            connection.send(new Message(MessageType.NAME_ACCEPTED));
                            return message.getData();
                        }
                    }
                }
            }
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException{
            //9.2
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
                //9.3
                Message message = new Message(MessageType.USER_ADDED, pair.getKey());
                //9.4
                //9.5
                if (!userName.equals(pair.getKey()))
                        connection.send(message);
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException{
            while (true) {
                //10.1
                Message message = connection.receive();
                //10.2
                if (message.getType().equals(MessageType.TEXT)){
                    //10.2
                    Message outMessage = new Message(MessageType.TEXT, userName +": "+ message.getData());
                    //10.3
                    sendBroadcastMessage(outMessage);
                }
                else
                    //10.4
                    ConsoleHelper.writeMessage("Error");


            }


        }

        @Override
        public void run() {
            String newUserName = null;
            //11.1
            ConsoleHelper.writeMessage("New connection established with: " + socket.getRemoteSocketAddress());
            //11.2
            try(Connection connection = new Connection(socket)) {
                //11.3
                newUserName = serverHandshake(connection);
                //11.4
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, newUserName));
                //11.5
                sendListOfUsers(connection,newUserName);
                //11.6
                serverMainLoop(connection,newUserName);
                //11.8
            } catch (IOException e) {
                e.printStackTrace();
                ConsoleHelper.writeMessage("Error with remote connection");
            } catch (ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Error with remote connection");
                e.printStackTrace();
            }
            //11.9
            connectionMap.remove(newUserName);
            sendBroadcastMessage(new Message(MessageType.USER_REMOVED, newUserName));
            //11.10
            ConsoleHelper.writeMessage("Remote connection closed");

        }
    }
}
