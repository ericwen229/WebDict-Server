package com.ericwen229.webdict.model;

public class UserStatus {

    private String status = "fail";
    private String userOnline = "fail";

    public UserStatus(boolean success, boolean userOnline) {
        if (success) {
            this.status = "success";
        }
        if (userOnline) {
            this.userOnline = "true";
        }
    }

    public String getStatus() {
        return status;
    }

    public String getUserOnline() {
        return userOnline;
    }

}
