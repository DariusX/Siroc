package com.zerses.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zerses.canonical.PersonFindRequest;
import com.zerses.entity.Person;

@Component
public class PersonDao {

    @Autowired
    private EntityManager em;

    public List<Person> findAll(PersonFindRequest personFindRequest) {
        Map<String, Object> parmValues = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select P from Person P where 1=1 ");
        if (personFindRequest.getAgeMin() != null) {
            sb.append(" and age >= :ageMin ");
            parmValues.put("ageMin", personFindRequest.getAgeMin());
        }
        if (personFindRequest.getAgeMax() != null) {
            sb.append(" and age <= :ageMax ");
            parmValues.put("ageMax", personFindRequest.getAgeMax());
        }
        String namePattern = personFindRequest.getName();
        if (namePattern != null) {
            namePattern = namePattern.trim();
            if (namePattern.length() > 0) {
                sb.append(" and name LIKE :name ");
                parmValues.put("name", namePattern);
            }
        }
        final TypedQuery<Person> jpaQuery = em.createQuery(sb.toString(), Person.class);
        for (Map.Entry<String, Object> nxtParam : parmValues.entrySet()) {
            jpaQuery.setParameter(nxtParam.getKey(), nxtParam.getValue());
        }
        return jpaQuery.getResultList();
    }
}
