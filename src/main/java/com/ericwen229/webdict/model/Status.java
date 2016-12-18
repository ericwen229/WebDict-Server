package com.ericwen229.webdict.model;

public class Status {

    private String status;
    private String msg;

    public Status(boolean success, String message) {
        if (success) {
            status = "success";
        }
        else {
            status = "fail";
        }
        this.msg = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
