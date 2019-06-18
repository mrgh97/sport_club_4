package com.example.jpademo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Gym extends ResourceSupport implements Serializable {

    public Gym(String name,String location){
        this.name = name;
        this.location = location;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    private String name;

    private String location;

    @Override
    @JsonIgnore
    public Link getId() {
        return this.getLink("self");
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
