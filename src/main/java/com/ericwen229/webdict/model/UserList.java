package com.ericwen229.webdict.model;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<String> users = new ArrayList<>();

    public void addUser(String user) {
        users.add(user);
    }

    public List<String> getUsers() {
        return users;
    }

}
