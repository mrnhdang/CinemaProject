package com.spring.cinemaproject.Models;


import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Tickets  {
    @Id
    @Column(name = "ticketID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketID;
    @Column(name = "ticketName")
    private String ticketName  ;
    @Column(name = "createDate")
    private Date create_date;
    @Column(name = "status")
    private int status ;

    @ManyToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "ticketdetails",
                joinColumns =@JoinColumn(name = "ticketID"),
                inverseJoinColumns = @JoinColumn(name = "chairID")
    )
    private Set<Chairs> chairs = new HashSet<>();

    @ManyToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "billdetails",
            joinColumns =@JoinColumn(name = "ticketID",referencedColumnName ="ticketID" ),
            inverseJoinColumns = @JoinColumn(name = "billID",referencedColumnName = "billID")
    )
    private Set<Bills> bills = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="showTimeID", referencedColumnName = "scheduleID")
    private Schedules schedules;

    public Tickets(){

    }

    public Tickets(Integer ticketID, String ticketName, Date create_date, int status) {
        this.ticketID = ticketID;
        this.ticketName = ticketName;
        this.create_date = create_date;
        this.status = status;
    }

    public Integer getTicketID() {
        return ticketID;
    }

    public void setTicketID(Integer ticketID) {
        this.ticketID = ticketID;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Chairs> getChairs() {
        return chairs;
    }

    public void setChairs(Set<Chairs> chairs) {
        this.chairs = chairs;
    }

    public Set<Bills> getBills() {
        return bills;
    }

    public void setBills(Set<Bills> bills) {
        this.bills = bills;
    }

    public Schedules getSchedules() {
        return schedules;
    }

    public void setSchedules(Schedules schedules) {
        this.schedules = schedules;
    }
}
