package com.zerses.canonical;

import java.io.Serializable;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;


@CsvRecord(separator = ",")
public class PersonAddRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @DataField(pos=2)
    private Integer age;
    
    @DataField(pos=1, defaultValue="", trim=true)
    private String name;

    public PersonAddRequest() {
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonAddRequest [ age=" + age + ", name=" + name + "]";
    }

}
