package com.ericwen229.webdict.model;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<String> onlineUsers = new ArrayList<>();
    private List<String> offlineUsers = new ArrayList<>();

    public void addOnlineUser(String user) {
        onlineUsers.add(user);
    }

    public void addOfflineUser(String user) { offlineUsers.add(user); }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public List<String> getOfflineUsers() { return offlineUsers; }

}
