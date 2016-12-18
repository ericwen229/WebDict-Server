package com.ericwen229.webdict.model;

public class RequestStatus {

    private String status;
    private String msg;

    public RequestStatus(boolean success, String message) {
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
