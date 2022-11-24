package com.spring.cinemaproject.Models;

import javax.persistence.*;

@Entity
@Table(name = "memberships")
public class Memberships {
    @Id
    @Column(name = "membershipID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membershipID;
    @Column(name = "membershipName")
    private String menbershipName;
    @Column(name = "points")
    private int points;
    @OneToOne(mappedBy = "memberships")
    private Users users;

    public Memberships(){

    }
    public Memberships(Integer membershipID, String menbershipName, int points) {
        this.membershipID = membershipID;
        this.menbershipName = menbershipName;
        this.points = points;
    }

    public Integer getMembershipID() {
        return membershipID;
    }

    public void setMembershipID(Integer membershipID) {
        this.membershipID = membershipID;
    }

    public String getMenbershipName() {
        return menbershipName;
    }

    public void setMenbershipName(String menbershipName) {
        this.menbershipName = menbershipName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
