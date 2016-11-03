package com.zerses;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class BodyTransformProcessor<I, O> implements Processor {

    private Class<I> inClass;

    public BodyTransformProcessor(Class<I> inClass) {
        this.inClass = inClass;
    }

    @Override
    public final void process(Exchange exchange) throws Exception {

        I body = exchange.getIn().getBody(inClass);
        if (body != null)
        {
            O outBody = processBody(body);
            exchange.getIn().setBody(outBody);
        }
    }
    
    protected O processBody(I body) throws Exception
    {
        O o  = null;
        return o;
    }

}
