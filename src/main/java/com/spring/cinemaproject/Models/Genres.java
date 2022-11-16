package com.spring.cinemaproject.Models;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genres {
    @Id
    @Column(name = "genreID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreID;
    @Column
    private String genreName;
    @Column
    private String genreName1;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "genredetails",
            joinColumns = @JoinColumn(name = "genreID"),
            inverseJoinColumns = @JoinColumn(name = "filmID")
    )
    private Set<Films> films=new HashSet<>();
    public Genres(){

    }

    public Set<Films> getFilms() {
        return films;
    }

    public void setFilms(Set<Films> films) {
        this.films = films;
    }

    public Genres(Integer genreID, String genreName) {
        this.genreID = genreID;
        this.genreName = genreName;
    }

    public Integer getGenreID() {
        return genreID;
    }

    public void setGenreID(Integer genreID) {
        this.genreID = genreID;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreName1() {
        return genreName1;
    }

    public void setGenreName1(String genreName1) {
        this.genreName1 = genreName1;
    }
}
