package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<Genres,Integer> {
    @Query("select g from Genres g where g.genreID = ?1")
    Genres findGenresByID(Integer id);
}
