package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Cinemas;
import com.spring.cinemaproject.Models.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CinemaRepository extends JpaRepository<Cinemas,Integer> {
    @Query("select  f from Cinemas f where f.cinemaID=?1")
    Cinemas findCinemasByID(Integer id);
}
