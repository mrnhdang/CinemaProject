package com.spring.cinemaproject.Models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "payments")
public class Payments {
    @Id
    @Column(name = "paymentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID;
    private String paymentName;
    private String typeOfPayment;
    @OneToMany(mappedBy = "payments", cascade = CascadeType.ALL)
    private Collection<Bills> bills;

    public Payments(){

    }
    public Payments(Integer paymentID, String paymentName, String typeOfPayment) {
        this.paymentID = paymentID;
        this.paymentName = paymentName;
        this.typeOfPayment = typeOfPayment;
    }

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public Collection<Bills> getBills() {
        return bills;
    }

    public void setBills(Collection<Bills> bills) {
        this.bills = bills;
    }
}
