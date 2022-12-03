package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chairs")
public class Chairs {
    @Id
    @Column(name = "chairID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chairID;
    @Column(name = "chairName")
    private String chairName;
    @Column(name = "status")
    private int status;
    @Column(name = "type")
    private  int type;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roomID")
    private Rooms rooms;

    @JsonIgnore
    @ManyToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(
            name = "ticketdetails",
            joinColumns = @JoinColumn(name = "chairID"),
            inverseJoinColumns = @JoinColumn(name = "ticketID")
    )
    private Set<Tickets> tickets=new HashSet<>();
    public Chairs(){

    }
    public Chairs(Integer chairID, String chair_name, int status, int type) {
        this.chairID = chairID;
        this.chairName = chair_name;
        this.status = status;
        this.type = type;
    }

    public Integer getChairID() {
        return chairID;
    }

    public void setChairID(Integer chairID) {
        this.chairID = chairID;
    }

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chair_name) {
        this.chairName = chair_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public Rooms getRooms() {
        return rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }

    public Set<Tickets> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Tickets> tickets) {
        this.tickets = tickets;
    }
}
