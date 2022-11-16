package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genres,Integer> {

}
