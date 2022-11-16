package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bills")
public class Bills {
    @Id
    @Column(name = "billID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billID;
    @Column(name = "bill_name")
    private String billName;
    @Column(name = "bill_total")
    private float billTotal;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name ="status")
    private Integer status;
    @Column(name = "note")
    private String note;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucherID", referencedColumnName = "voucherID")
    private Vouchers vouchers;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentID", referencedColumnName = "paymentID")
    private Payments payments;
    @ManyToOne
    @JoinColumn(name = "userID")
    private Users users;

    @ManyToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "billdetails",
            joinColumns =@JoinColumn(name = "billID", referencedColumnName = "billID"),
            inverseJoinColumns = @JoinColumn(name = "ticketID",referencedColumnName = "ticketID")
    )
    private Set<Tickets> tickets = new HashSet<>();

    public Bills(){

    }
    public Bills(Integer billID, String billName, float billTotal, Date createDate, Integer status, String note ) {
        this.billID = billID;
        this.billName = billName;
        this.billTotal = billTotal;
        this.status = status;
        this.createDate = createDate;
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBillID() {
        return billID;
    }

    public void setBillID(Integer billID) {
        this.billID = billID;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public Vouchers getVouchers() {
        return vouchers;
    }

    public void setVouchers(Vouchers vouchers) {
        this.vouchers = vouchers;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Set<Tickets> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Tickets> tickets) {
        this.tickets = tickets;
    }

    public float getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(float billTotal) {
        this.billTotal = billTotal;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Payments getPayments() {
        return payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
