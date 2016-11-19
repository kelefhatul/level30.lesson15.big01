package com.javarush.test.level30.lesson15.big01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by root on 11/18/2016.
 */
public class ConsoleHelper {
    private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString(){
        String inData = "";
        while (true){
            try {
                inData = keyboard.readLine();
                break;
            } catch (IOException e) {
                writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            }
        }
        return inData;
    }

    public static int readInt(){
        int result = 0;
        while (true){
            try {
                result = Integer.parseInt(readString());
                break;
            } catch (NumberFormatException e) {
                writeMessage("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
            }
        }
        return result;
    }
}
