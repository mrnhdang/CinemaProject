package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="users")
public class Users {
    @Id
    @Column(name = "userID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;
    @Column(nullable = false, length = 50)
    private String userName;
    @Column(nullable = false,unique = false, length = 64)
    private String password;
    @Column(nullable = false,unique = false, length = 50)
    private String address;
    @Column(nullable = false,unique = true, length = 50)
    private String email;
    @Column
    private Date createDate;
    private String verificationCode;
    private boolean status;

    @JsonManagedReference
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Vouchers> vouchers = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "employeedetails",
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "employeeID")
    )
    private Set<Employees> employees=new HashSet<>();

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membershipID", referencedColumnName = "membershipID")
    private Memberships memberships;

    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Collection<Bills> bills;

    public Users(){

    }
    public Users(Integer userID, String userName, String userPass, String address, String email, Date createTime, String verificationCode) {
        this.userID = userID;
        this.userName = userName;
        this.password = userPass;
        this.address = address;
        this.email = email;
        this.createDate = createTime;
        this.verificationCode= verificationCode;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return password;
    }

    public void setUserPass(String userPass) {
        this.password = userPass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createTime) {
        this.createDate= createTime;
    }

    public Set<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employees> employees) {
        this.employees = employees;
    }

    public Memberships getMemberships() {
        return memberships;
    }

    public void setMemberships(Memberships memberships) {
        this.memberships = memberships;
    }

    public Collection<Bills> getBills() {
        return bills;
    }

    public void setBills(Collection<Bills> bills) {
        this.bills = bills;
    }

    public Set<Vouchers> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Vouchers> vouchers) {
        this.vouchers = vouchers;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
