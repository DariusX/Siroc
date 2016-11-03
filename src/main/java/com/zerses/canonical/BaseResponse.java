package com.zerses.canonical;

import java.io.Serializable;

public class BaseResponse implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private boolean success;
    private String errorMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }



    
}
