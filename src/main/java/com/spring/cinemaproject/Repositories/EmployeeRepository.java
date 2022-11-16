package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employees, Integer> {

    @Query("select  e from Employees e where e.employeeName =?1")
    Employees findEmployeesByEmployeeName(String name);
}
