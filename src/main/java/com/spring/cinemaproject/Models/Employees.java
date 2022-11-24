package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.catalina.User;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employees {
    @Id
    @Column(name = "employeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeID;
    @Column(name = "employeeName")
    private String employeeName;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "employeedetails",
            joinColumns = @JoinColumn(name = "employeeID"),
            inverseJoinColumns = @JoinColumn(name = "userID")
    )
    private Set<Users> users=new HashSet<>();
    public Employees(){

    }

    public Employees(Integer employeeID, String employeeName, Set<Users> users) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.users = users;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
}
