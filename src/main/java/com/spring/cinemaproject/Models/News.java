package com.spring.cinemaproject.Models;

import javax.persistence.*;

@Entity
public class News {
    @Id
    @Column(name = "newID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newID;
    @Column(name = "new_name")
    private String newName;
    @Column(name = "new_name1")
    private String newName1;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;

    public News (){

    }

    public News(Integer newID, String newName, String newName1, String description, String image) {
        this.newID = newID;
        this.newName = newName;
        this.newName1 = newName1;
        this.description = description;
        this.image = image;
    }

    public Integer getNewID() {
        return newID;
    }

    public void setNewID(Integer newID) {
        this.newID = newID;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewName1() {
        return newName1;
    }

    public void setNewName1(String newName1) {
        this.newName1 = newName1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
