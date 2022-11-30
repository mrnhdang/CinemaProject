package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name ="foods")
public class Foods {
    @Id
    @Column(name = "foodID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer foodID;
    @Column(name = "foodName")
    private String foodName;
    @Column(name = "foodName1")
    private String foodName1;
    @Column(name = "foodPrice")
    private float foodprice;
    @Column(name = "imageFood")
    private String image;
    private  int status;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "combodetails",
            joinColumns = @JoinColumn(name = "foodID"),
            inverseJoinColumns = @JoinColumn(name = "comboID"))
    private Set<Combos> combos;

    public Foods(){

    }
    public Foods(Integer foodID, String foodName, String foodName1, float foodprice,String image, int status) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodName1 = foodName1;
        this.foodprice = foodprice;
        this.image=image;
        this.status = status;
    }

    public Integer getFoodID() {
        return foodID;
    }

    public void setFoodID(Integer foodID) {
        this.foodID = foodID;
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

    public float getFoodprice() {
        return foodprice;
    }

    public void setFoodprice(float foodprice) {
        this.foodprice = foodprice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Combos> getCombos() {
        return combos;
    }

    public void setCombos(Set<Combos> combos) {
        this.combos = combos;
    }
}
