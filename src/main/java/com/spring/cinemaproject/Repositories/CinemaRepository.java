package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Addresses;
import com.spring.cinemaproject.Models.Cinemas;
import com.spring.cinemaproject.Models.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

public interface CinemaRepository extends JpaRepository<Cinemas,Integer> {
    @Query("select  f from Cinemas f where f.cinemaID=?1")
    Cinemas findCinemasByID(Integer id);

    @Query("select a from Addresses a where a not in (select c.addresses from Cinemas c )")
    List<Addresses> findAllAddressNotUsed();

    @Query("select a from Contacts a where a not in (select c.contacts from Cinemas c )")
    List<Contacts> findAllContactNotUsed();
}
