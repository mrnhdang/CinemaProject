package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Cinemas;
import com.spring.cinemaproject.Models.Films;
import com.spring.cinemaproject.Models.Rooms;
import com.spring.cinemaproject.Models.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.*;

public interface ScheduleRepository extends JpaRepository<Schedules,Integer> {
    @Query("select f from Schedules f where f.films.filmID=?1")
    List<Schedules> findAllScheduleByFilmID(Integer id);

    @Query("select s from Schedules s where s.showTime = ?1 and s.films.filmID = ?2")
    Schedules finScheduleByTimeAndFilmID(Date date, Integer filmID);

    @Query("select s from Schedules s where s.showTime = ?1 and s.rooms.roomID = ?2 and s.films.filmID = ?3")
    Schedules findScheduleForAjax(Date date, Integer roomID, Integer filmID);

    @Query("select s from Schedules s where s.scheduleID = ?1")
    Schedules findsScheduleByID(Integer id);

    @Query("select s from Schedules s where s.films = ?1")
    List<Schedules> findSchedulesByFilms(Films films);

    @Query("select s.rooms.cinemas from Schedules s where s.films=?1")
    Set<Cinemas> findCinemaByFilm(Films films);

    @Query("select s from Schedules s where s.rooms.roomID =?1 and s.showTime = ?2")
    Schedules validateSchedule(Integer roomID, Date showTime);

    @Query("select distinct r from Schedules s join Rooms r where r not in (select s1.rooms from Schedules s1) ")
    List<Rooms> findRoomsNotInSchedule();

}
