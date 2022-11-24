package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "cinemas")
public class Cinemas {
    @Id
    @Column(name = "cinemaID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cinemaID;
    @Column(name = "cinemaName")
    private String cinemaName;
    @Column(name = "cinemaName1")
    private String cinemaName1;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "contactID")
    private Contacts contacts;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "addressID")
    private Addresses addresses;

    @JsonManagedReference
    @OneToMany(mappedBy = "cinemas", cascade = CascadeType.ALL)
    private Collection<Rooms> rooms;
    public Cinemas(){

    }
    public Cinemas(Integer cinemaID, String cinemaName, String cinemaName1, Contacts contacts, Addresses addresses) {
        this.cinemaID = cinemaID;
        this.cinemaName = cinemaName;
        this.cinemaName1 = cinemaName1;
        this.contacts = contacts;
        this.addresses = addresses;
    }

    public Addresses getAddresses() {
        return addresses;
    }

    public void setAddresses(Addresses addresses) {
        this.addresses = addresses;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public Integer getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(Integer cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaName1() {
        return cinemaName1;
    }

    public void setCinemaName1(String cinemaName1) {
        this.cinemaName1 = cinemaName1;
    }
}
