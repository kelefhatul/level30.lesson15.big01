package com.javarush.test.level30.lesson15.big01.client;

/**
 * Created by root on 11/19/2016.
 */
public class BotClient extends Client {
    private static int nameCounter =0;

    public class BotSocketThread extends SocketThread{


    }


    @Override
    protected SocketThread getSocketThread(){
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSentTextFromConsole(){
        return false;
    }

    @Override
    protected String getUserName(){
        if (nameCounter == 99) nameCounter=0;
        return "date_bot_"+nameCounter++;
    }




    public static void main(String[] args) {
        new BotClient().run();
    }
}
