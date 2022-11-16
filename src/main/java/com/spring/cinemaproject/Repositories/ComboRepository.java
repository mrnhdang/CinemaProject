package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Chairs;
import com.spring.cinemaproject.Models.Combos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComboRepository extends JpaRepository<Combos,Integer> {
    @Query("select  f from Combos f where f.comboID=?1")
    Combos findCombosByID(Integer id);
}
