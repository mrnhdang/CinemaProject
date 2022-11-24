package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Rooms {
    @Id
    @Column(name = "roomID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomID;
    @Column(name = "roomName")
    private String roomName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cinemaID")
    private Cinemas cinemas;

    @JsonIgnore
    @OneToMany(mappedBy = "rooms",cascade = CascadeType.ALL)
    private Collection<Chairs> chairs;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "roomdetails",
                joinColumns = @JoinColumn(name = "roomID"),
                inverseJoinColumns = @JoinColumn(name = "filmID"))
    private Set<Films> films = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "rooms",cascade = CascadeType.ALL)
    private Collection<Schedules> schedules;

    public Cinemas getCinemas() {
        return cinemas;
    }

    public void setCinemas(Cinemas cinemas) {
        this.cinemas = cinemas;
    }

    public Rooms(){

    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Rooms(Integer roomID, String roomName) {
        this.roomID = roomID;
        this.roomName = roomName;
    }

    public Collection<Chairs> getChairs() {
        return chairs;
    }

    public void setChairs(Collection<Chairs> chairs) {
        this.chairs = chairs;
    }

    public Set<Films> getFilms() {
        return films;
    }

    public void setFilms(Set<Films> films) {
        this.films = films;
    }

    public Collection<Schedules> getSchedules() {
        return schedules;
    }

    public void setSchedules(Collection<Schedules> schedules) {
        this.schedules = schedules;
    }
}
