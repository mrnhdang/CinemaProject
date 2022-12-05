package com.spring.cinemaproject.Models;

import javax.persistence.*;

@Entity
@Table(name = "news")
public class News {
    @Id
    @Column(name = "newID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newID;
    @Column(name = "newName")
    private String newName;
    @Column(name = "newName1")
    private String newName1;
    @Column(name = "newTitle")
    private String newTitle;
    @Column(name = "newTitle1")
    private String newTitle1;
    @Column(name = "image")
    private String image;

    public News (){

    }

    public News(Integer newID, String newName, String newName1, String newTitle, String newTitle1,  String image) {
        this.newID = newID;
        this.newName = newName;
        this.newName1 = newName1;
        this.newTitle = newTitle;
        this.newTitle1 = newTitle1;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewTitle1() {
        return newTitle1;
    }

    public void setNewTitle1(String newTitle1) {
        this.newTitle1 = newTitle1;
    }
}
