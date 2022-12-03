package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Foods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodRepository extends JpaRepository<Foods,Integer> {
    @Query("select  f from Foods f where f.foodID=?1")
    Foods findFoodsByID(Integer id);
}
