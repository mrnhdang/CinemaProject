package com.spring.cinemaproject.Models;

import javax.persistence.*;

@Entity
@Table(name = "foodbills")
public class Foodbills {
    @Id
    @Column(name = "foodbillID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer foodbillID;
    private String foodName ;
    private String foodName1;
    private float price ;

    @ManyToOne
    @JoinColumn(name = "billID", referencedColumnName = "billID")
    private Bills bills;

    @ManyToOne
    @JoinColumn(name = "userID",  referencedColumnName = "userID")
    private Users users;

    public Foodbills (){

    }

    public Foodbills(Integer foodbillID, String foodName, String foodName1, float price, Bills bills, Users users) {
        this.foodbillID = foodbillID;
        this.foodName = foodName;
        this.foodName1 = foodName1;
        this.price = price;
        this.bills = bills;
        this.users = users;
    }

    public Integer getFoodbillID() {
        return foodbillID;
    }

    public void setFoodbillID(Integer foodbillID) {
        this.foodbillID = foodbillID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName1() {
        return foodName1;
    }

    public void setFoodName1(String foodName1) {
        this.foodName1 = foodName1;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Bills getBills() {
        return bills;
    }

    public void setBills(Bills bills) {
        this.bills = bills;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
