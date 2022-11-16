package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillRepository extends JpaRepository<Bills, Integer> {
    @Query("select b from Bills b where b.billID= ?1")
    Bills findBillsByID(Integer id);
}
