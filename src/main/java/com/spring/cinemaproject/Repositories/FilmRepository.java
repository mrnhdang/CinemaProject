package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface FilmRepository extends JpaRepository<Films, Integer> {
    @Query("select  f from Films f where f.filmID=?1")
    Films findFilmsByID(Integer id);

    @Query("select f from Films f where ?1 between f.releaseDate and f.endDate")
    List<Films> findFilmsOnSchedule(Date date);

    @Query("select f from Films f where f.filmName =?1")
    List<Films> findByFilmName(String trim);

    @Query("SELECT f FROM Films f WHERE CONCAT(f.filmName,' ',f.filmName1,' ',f.directors,' ',f.producers,' ',f.genres,' ', f.price) LIKE %?1%")
    List<Films> search(String keyword);
}
