package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "directors")
public class Directors {
    @Id
    @Column(name = "directorID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer directorID;
    @Column(name = "directorName")
    private String directorName;

    @OneToMany(mappedBy = "directors",cascade = CascadeType.ALL)
    private Collection<Films> films;

    public Directors(){

    }

    public Directors(Integer directorID, String directorName, Collection<Films> films) {
        this.directorID = directorID;
        this.directorName = directorName;
        this.films = films;
    }

    public Collection<Films> getFilms() {
        return films;
    }

    public void setFilms(Collection<Films> films) {
        this.films = films;
    }


    public Integer getDirectorID() {
        return directorID;
    }

    public void setDirectorID(Integer directorID) {
        this.directorID = directorID;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
}
