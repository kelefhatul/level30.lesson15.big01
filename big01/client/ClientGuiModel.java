package com.javarush.test.level30.lesson15.big01.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 11/19/2016.
 */
public class ClientGuiModel {
    private final Set<String> allUserNames = new HashSet<>();
    private String newMessage;

    public Set<String> getAllUserNames() {
        return Collections.unmodifiableSet(allUserNames);
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public void addUser(String newUserName){
        if (newUserName != null){
            allUserNames.add(newUserName);
        }
    }

    public void deleteUser(String userName){
        if (userName != null && allUserNames.size() > 0){
            allUserNames.remove(userName);
        }
    }
}
