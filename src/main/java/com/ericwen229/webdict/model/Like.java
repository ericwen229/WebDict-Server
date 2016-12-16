package com.ericwen229.webdict.model;

public class Like {

    private String status;

    public Like(boolean success) {
        if (success) {
            status = "success";
        }
        else {
            status = "fail";
        }
    }

    public String getStatus() {
        return status;
    }

}
