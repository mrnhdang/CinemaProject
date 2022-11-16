package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Addresses {
    @Id
    @Column(name = "addressID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer addressID;
    @Column
    private String addressName;
    @Column
    private String addressName1;

    @JsonBackReference
    @OneToOne(mappedBy = "addresses", cascade = CascadeType.ALL)
    private Cinemas cinemas;

    public Addresses(){

    }
    public Addresses(Integer addressID, String addressName, String addressName1, Cinemas cinemas) {
        this.addressID = addressID;
        this.addressName = addressName;
        this.addressName1 = addressName1;
        this.cinemas = cinemas;
    }

    public Cinemas getCinemas() {
        return cinemas;
    }

    public void setCinemas(Cinemas cinemas) {
        this.cinemas = cinemas;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressName1() {
        return addressName1;
    }

    public void setAddressName1(String addressName1) {
        this.addressName1 = addressName1;
    }
}
