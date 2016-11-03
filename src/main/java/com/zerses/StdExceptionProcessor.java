package com.zerses;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

import com.zerses.canonical.BaseResponse;

public class StdExceptionProcessor implements Processor {

    private BaseResponse response;
    
    public StdExceptionProcessor(BaseResponse response)
    {
        this.response = response;
    }
    
    @Override
    public void process(Exchange exchange) throws Exception {


        if (response == null)
        {
          response = new BaseResponse();
        }
        Logger logger = Logger.getLogger(response.getClass());
        
        Throwable exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        String exceptionEndpoint = exchange.getProperty(Exchange.FAILURE_ENDPOINT, String.class);
        if (exceptionEndpoint == null)
        {
            exceptionEndpoint = "";
        }
        if (exception != null) {
            logger.error("Exception in Exchange " + exchange.getExchangeId() +", Endpoint:"+exceptionEndpoint, exception);
        }


        response.setSuccess(false);
        response.setErrorMessage("Error occurred!");
        exchange.getOut().setBody(response);
    }

}
