package com.javarush.test.level30.lesson15.big01.client;

import com.javarush.test.level30.lesson15.big01.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by root on 11/19/2016.
 */
public class BotClient extends Client {
    private static int nameCounter =0;
    private Calendar currentDate = new GregorianCalendar();


    public class BotSocketThread extends SocketThread{
        @Override //19.1
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override //19.2
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            //19.2.2
            String nameFromMessage = "";
            String textFromMessage = "";
            if (message.contains(": ")) {
                nameFromMessage = message.substring(0, message.indexOf(": "));
                textFromMessage = message.substring(message.indexOf(":") + 2);
            } else {
                textFromMessage = message;
            }
            SimpleDateFormat format = null;
            // Отправить ответ в зависимости от текста принятого сообщения. Если текст сообщения:
            if ("дата".equalsIgnoreCase(textFromMessage)) {
                format = new SimpleDateFormat("d.MM.YYYY");
            }
            else if ("день".equalsIgnoreCase(textFromMessage)) {
                format = new SimpleDateFormat("d");
            }
            else if ("месяц".equalsIgnoreCase(textFromMessage)) {
                format = new SimpleDateFormat("MMMM");
            }
            else if ("год".equalsIgnoreCase(textFromMessage)) {
                format = new SimpleDateFormat("YYYY");
            }
            else if ("время".equalsIgnoreCase(textFromMessage)) {
                format = new SimpleDateFormat("H:mm:ss");
            }
            else if ("час".equalsIgnoreCase(textFromMessage)) {
                format = new SimpleDateFormat("H");
            }
            else if ("минуты".equalsIgnoreCase(textFromMessage)) {
                format = new SimpleDateFormat("m");
            }
            else if ("секунды".equalsIgnoreCase(textFromMessage)) {
                format = new SimpleDateFormat("s");
            }

            if (format != null)
            {
                sendTextMessage("Информация для " + nameFromMessage + ": " + format.format(Calendar.getInstance().getTime()));
            }

        }
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
