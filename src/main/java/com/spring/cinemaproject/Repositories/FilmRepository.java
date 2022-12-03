package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Films;
import com.spring.cinemaproject.Models.Schedules;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface FilmRepository extends JpaRepository<Films, Integer> {
    @Query("select  f from Films f where f.filmID=?1")
    Films findFilmsByID(Integer id);

    @Query("select f from Films f where ?1 between f.releaseDate and f.endDate ")
    List<Films> findFilmsOnSchedule(Date date);

    @Query("select f from Films f where f.filmName =?1")
    List<Films> findByFilmName(String trim);

    @Query("SELECT f FROM Films f WHERE CONCAT(f.filmName,' ',f.filmName1,' ',f.directors,' ',f.producers,' ',f.price) LIKE %?1%")
    List<Films> search(String keyword);

    @Query("SELECT f FROM Films f WHERE CONCAT(f.filmName,' ',f.filmName1,' ',f.directors,' ',f.producers,' ',f.price) LIKE %?1%")
    Page<Films> searchPaginated(String keyword,Pageable pageable);

    @Query("Select f from Films f where f.releaseDate > current_date ")
    List<Films> findFilmsNotSchedule();

    @Query("select f.schedules from Films f where f = ?1")
    Set<Schedules> findScheduleByFilm(Films films);
}
