package com.zerses.person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.ws.rs.core.MediaType;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerses.ApplicationBoot;
import com.zerses.canonical.PersonAddRequest;
import com.zerses.canonical.PersonAddResponse;
import com.zerses.canonical.PersonFindRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationBoot.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class PersonRouteTest  {
    
    @Autowired
    private CamelContext camelContext;
    
    private static Logger logger = Logger.getLogger(PersonRouteTest.class);

   @Test
    public void testFind() throws InterruptedException {

        PersonFindRequest req = new PersonFindRequest();
        req.setAgeMin(19);
        req.setAgeMax(39);

        String result = invokeRestGet("/persons?ageMin=57&ageMax=80");
        logger.info(result);
        assert (result.contains("Response Code=200"));
        assert (result.toString().contains("Test ONE Person"));

    }
   
   @Test
   public void testFindOne() throws InterruptedException {

       PersonFindRequest req = new PersonFindRequest();
       req.setAgeMin(19);
       req.setAgeMax(39);

       String result = invokeRestGet("/person/1");
       logger.info(result);
       assert (result.contains("Response Code=200"));
       assert (result.toString().contains("Test ONE Person"));

   }
    
    @Test
    public void testAddDirect() throws InterruptedException {
        
        PersonAddRequest req = new PersonAddRequest();
        req.setName("Missy Charles");
        req.setAge(58);
        ProducerTemplate template = camelContext.createProducerTemplate();
        PersonAddResponse resp = template.requestBody("direct:personAdd", req, PersonAddResponse.class);
        System.out.println(" RESPONSE WAS "+resp.isSuccess());
        Thread.sleep(20000);
        assert(resp.isSuccess());
    }
    
    @Test
    public void testAddRest() throws InterruptedException {


        PersonAddRequest req = new PersonAddRequest();
        req.setName("Tammy Jones");
        req.setAge(35);

        String result = invokeRestPost("/person", req);
        logger.info(result);
        assert (result.contains("Response Code=500"));
    }

    private String invokeRestGet(String path) {
        try {
            String url = "http://localhost:9000"+path;

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            // add request header
            request.addHeader("Accept", MediaType.APPLICATION_JSON);
            //request.addHeader("Content-Type", MediaType.APPLICATION_JSON);

            HttpResponse response = client.execute(request);

            return handleHttpResponse(response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "---- ERROR ----";
    }
    
    private String invokeRestPost(String path, Serializable req) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Object to JSON in String
            String jsonReq = mapper.writeValueAsString(req);
            logger.info("jsonReq=" + jsonReq);
            
            String url = "http://localhost:9000"+path;

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.setEntity(new StringEntity(jsonReq));

            // add request header
            request.addHeader("Accept", MediaType.APPLICATION_JSON);
            request.addHeader("Content-Type", MediaType.APPLICATION_JSON);

            HttpResponse response = client.execute(request);

            return handleHttpResponse(response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "---- ERROR ----";
    }

    private static String handleHttpResponse(HttpResponse response) throws IOException {
        int responseCode = response.getStatusLine().getStatusCode();
        logger.info("Response Code : " + response.getStatusLine().getStatusCode());


        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer("Response Code="+responseCode+"\n");
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        logger.info("result: " + result);
 

        return result.toString();
    }

}
