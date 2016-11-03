package com.zerses.canonical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zerses.entity.Person;

public class PersonFindResponse extends BaseResponse implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private List<Person> personList;

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
    
    public void addToPersonList(Person person) {
        if (this.personList == null)
        {
            this.personList = new ArrayList<>();
        }
        this.personList.add(person);
    }

    @Override
    public String toString() {
        return "PersonFindResponse [personList=" + personList + "]";
    }
    
    
}
