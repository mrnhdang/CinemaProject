package com.spring.cinemaproject.Models;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class Contacts {
    @Id
    @Column(name = "contactID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactID;
    @Column(name = "contactName")
    private String contactName;
    @Column(name = "contactName1")
    private String contactName1;


    @OneToOne(mappedBy = "contacts",cascade = CascadeType.ALL)
    private Cinemas cinemas;

    public Contacts(){

    }
    public Contacts(Integer contactID, String contactName, String contactName1, Cinemas cinemas) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactName1 = contactName1;
        this.cinemas = cinemas;
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName1() {
        return contactName1;
    }

    public void setContactName1(String contactName1) {
        this.contactName1 = contactName1;
    }

    public Cinemas getCinemas() {
        return cinemas;
    }

    public void setCinemas(Cinemas cinemas) {
        this.cinemas = cinemas;
    }
}
