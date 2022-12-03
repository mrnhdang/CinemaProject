package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vouchers")
public class Vouchers {
    @Id
    @Column(name = "voucherID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy =  "org.hibernate.id.UUIDGenerator")
    private String voucherID;
    @Column(name = "voucherName")
    private String voucherName;
    @Column(name = "voucherName1")
    private String voucherName1;
    @Column(name = "discount")
    private float discount;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "effectiveDate")
    private Date effectiveDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "expiryDate")
    private Date expireDate;
    @Column(name = "amount")
    private int amount;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private Users users;

    @JsonBackReference
    @OneToOne(mappedBy = "vouchers")
    private Bills bills;
    public Vouchers(){

    }
    public Vouchers(String voucherID, String voucherName, String voucherName1, float discount, Date effectiveDate, Date expireDate, int amount) {
        this.voucherID = voucherID;
        this.voucherName = voucherName;
        this.voucherName1 = voucherName1;
        this.discount = discount;
        this.effectiveDate = effectiveDate;
        this.expireDate = expireDate;
        this.amount = amount;
    }

    public String getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(String voucherID) {
        this.voucherID = voucherID;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getVoucherName1() {
        return voucherName1;
    }

    public void setVoucherName1(String voucherName1) {
        this.voucherName1 = voucherName1;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Bills getBills() {
        return bills;
    }

    public void setBills(Bills bills) {
        this.bills = bills;
    }
}
