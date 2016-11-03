package com.zerses.canonical;

import java.io.Serializable;

public class PersonAddResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public static PersonAddResponse createSuccessfulResponse()
    {
        PersonAddResponse response = new PersonAddResponse();
        response.setSuccess(true);
        return response;
    }
}
