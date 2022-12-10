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

    @Query("select s from Schedules s where s.rooms = ?1 ")
    List<Schedules> findScheduleByRoomAndFilm(Rooms rooms);

    @Query("select s from Schedules s where s.rooms = ?1 and s.films = ?2 and s.showTime = ?3 ")
    Schedules findScheduleByRoomAndFilmAndDate(Rooms rooms, Films films,Date date);

    @Query("select s from Schedules s where s.scheduleID = ?1")
    Schedules findsScheduleByID(Integer id);

    @Query("select s.rooms.cinemas from Schedules s where s.films=?1")
    Set<Cinemas> findCinemaByFilm(Films films);

    @Query("select s.rooms from Schedules s where s.films=?1")
    Set<Rooms> findCinemaByRooms(Films films);


}
