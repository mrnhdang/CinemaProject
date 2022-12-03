package com.spring.cinemaproject.Repositories;

import java.util.*;
import com.spring.cinemaproject.Models.Chairs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChairRepository extends JpaRepository<Chairs,Integer> {
    @Query("select  f from Chairs f where f.chairID=?1")
    Chairs findChairsByID(Integer id);

    @Query("select c from Chairs c where c.rooms.roomID = ?1")
    List<Chairs> findChairsByRooms(Integer roomID);

    @Modifying
    @Query("update Chairs c set c.status=2  where c.chairID = :id")
    void setChairsOccupied(@Param("id") Integer chairID);

}
