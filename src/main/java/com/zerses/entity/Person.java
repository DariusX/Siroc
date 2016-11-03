package com.zerses.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the person database table.
 */
@Entity
@NamedQueries({@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
               @NamedQuery(name = "Person.findByAgeRange", query = "SELECT p FROM Person p WHERE p.age between :age1 and :age2 ")})
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer personId;

    private Integer age;

    private String name;

    public Person() {
    }

    @Override
    public String toString() {
        return "Person [personId=" + personId + ", age=" + age + ", name=" + name + "]";
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
