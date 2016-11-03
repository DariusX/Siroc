package com.zerses.entity;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersonTest {

    private static EntityManagerFactory emfactory = null;
    private static EntityManager em = null;

    @BeforeClass
    public static void setUp() {
        emfactory = Persistence.createEntityManagerFactory("test1jpa");
        em = emfactory.createEntityManager();
    }

    @AfterClass
    public static void tearDown() {
        em.close();
        emfactory.close();
    }


    @Test
    public void testCreateFindRemove() {
        Person person = new Person();
        person.setName("Test Person");
        person.setAge(57);
        assert (person.getPersonId() == 0);

        //Create a new record
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        assert (person.getPersonId() != 0);

        //Find the newly-created record
        Person personFound = new Person();
        assert (person.getPersonId() != personFound.getPersonId());
        personFound = em.find(Person.class, new Integer(person.getPersonId()));
        assert (person.getPersonId() == personFound.getPersonId());

        //Remove the newly-created record
        em.getTransaction().begin();
        em.remove(person);
        em.getTransaction().commit();
        
        //Attempt to find again
        personFound = new Person();
        assert (person.getPersonId() != personFound.getPersonId());
        personFound = em.find(Person.class, new Integer(person.getPersonId()));
        assert(personFound == null);
        
    }

}
