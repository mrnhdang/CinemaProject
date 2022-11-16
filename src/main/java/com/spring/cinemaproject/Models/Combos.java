package com.spring.cinemaproject.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "combos")
public class Combos {
    @Id
    @Column(name = "comboID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comboID;
    private String comboName;
    private String comboName1;
    private float comboprice;
    private String image;
    private int status;

    @ManyToMany
    @JoinTable(
            name = "combodetails",
            joinColumns = @JoinColumn(name = "comboID",referencedColumnName = "comboID"),
            inverseJoinColumns = @JoinColumn(name = "foodID", referencedColumnName = "foodID"))
    private Set<Foods> foods;
    public Combos(){

    }
    public Combos(Integer comboID, String comboName, String comboName1, float comboprice, String image, int status) {
        this.comboID = comboID;
        this.comboName = comboName;
        this.comboName1 = comboName1;
        this.comboprice = comboprice;
        this.image= image;
        this.status = status;
    }

    public Integer getComboID() {
        return comboID;
    }

    public void setComboID(Integer comboID) {
        this.comboID = comboID;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public String getComboName1() {
        return comboName1;
    }

    public void setComboName1(String comboName1) {
        this.comboName1 = comboName1;
    }

    public float getComboprice() {
        return comboprice;
    }

    public void setComboprice(float comboprice) {
        this.comboprice = comboprice;
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

    public Set<Foods> getFoods() {
        return foods;
    }

    public void setFoods(Set<Foods> foods) {
        this.foods = foods;
    }
}
