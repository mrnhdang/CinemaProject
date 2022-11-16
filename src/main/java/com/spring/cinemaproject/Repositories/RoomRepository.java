package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Cinemas;
import com.spring.cinemaproject.Models.Films;
import com.spring.cinemaproject.Models.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

public interface RoomRepository extends JpaRepository<Rooms, Integer> {

    @Query("select r from Rooms r where r.films = ?1 and r.cinemas = ?2")
    Rooms findRoomByFilmIDAndCinemaID(Films film, Cinemas cinema);

    @Query("select r from Rooms r where  r.cinemas = ?1")
    List<Rooms> findRoomsByCinema(Cinemas cinemas);

    @Query("select r from Rooms r where r.roomID=?1")
    Rooms findRoomsByID(Integer id);
}
