package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "schedules")
public class Schedules {
    @Id
    @Column(name = "scheduleID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleID;
    @Column(name = "status")
    private int status;
    @Column(name = "priceTicket")
    private float price;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "showTime")
    private Date showTime;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "filmID")
    private Films films;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roomID")
    private Rooms rooms;

    @OneToMany(mappedBy = "schedules",cascade = CascadeType.ALL)
    private Collection<Tickets> tickets= new HashSet<>();
    public Schedules() {

    }

    public Schedules(Integer scheduleID, int status, Date showTime, float price) {
        this.scheduleID = scheduleID;
        this.status = status;
        this.showTime = showTime;
        this.price = price;
    }

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public Films getFilms() {
        return films;
    }

    public void setFilms(Films films) {
        this.films = films;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Collection<Tickets> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Tickets> tickets) {
        this.tickets = tickets;
    }
}
