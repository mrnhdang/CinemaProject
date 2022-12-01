package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "producers")
public class Producers {
    @Id
    @Column(name = "producerID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer producerID;
    @Column(name = "producerName")
    private String producerName;

    @JsonManagedReference
    @OneToMany(mappedBy = "producers",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Collection<Films> films;

    public Collection<Films> getFilms() {
        return films;
    }

    public void setFilms(Collection<Films> films) {
        this.films = films;
    }

    public Producers(){

        }
    public Producers(Integer producerID, String producerName, Collection<Films> films) {
        this.producerID = producerID;
        this.producerName = producerName;
        this.films = films;
    }

    public Integer getProducerID() {
        return producerID;
    }

    public void setProducerID(Integer producerID) {
        this.producerID = producerID;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }
}
