package com.zerses.canonical;

import java.io.Serializable;

import org.apache.camel.Header;

public class PersonFindRequest implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Integer ageMin;
    private Integer ageMax;
    private String name;
    
    
    public static PersonFindRequest createRequest(int id)
    {
        PersonFindRequest personFindRequest = new PersonFindRequest();
        return personFindRequest;
    }
    
    @Override
    public String toString() {
        return "PersonFindRequest [ ageMin=" + ageMin + ", ageMax=" + ageMax + ", name=" + name + "]";
    }
    
    public static PersonFindRequest fromHeaders(@Header("ageMin") Integer ageMin, @Header("ageMax") Integer ageMax, @Header("name") String name) {
        PersonFindRequest personFindRequest = new PersonFindRequest();
        if (ageMin != null) {
            personFindRequest.setAgeMin(ageMin);
        }
        if (ageMax != null) {
            personFindRequest.setAgeMax(ageMax);
        }
        if (name != null) {
            personFindRequest.setName(name);
        }

        return personFindRequest;
    }
    

    public Integer getAgeMin() {
        return ageMin;
    }
    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }
    public Integer getAgeMax() {
        return ageMax;
    }
    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
