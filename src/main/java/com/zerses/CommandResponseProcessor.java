package com.zerses;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.zerses.canonical.BaseResponse;

public class CommandResponseProcessor<T extends BaseResponse> implements Processor {

    private Class<T> responseClass;

    public CommandResponseProcessor(Class<T> responseClass) {
        this.responseClass = responseClass;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        T response = responseClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        response.setSuccess(true);
        exchange.getOut().setBody(response);

    }

}
