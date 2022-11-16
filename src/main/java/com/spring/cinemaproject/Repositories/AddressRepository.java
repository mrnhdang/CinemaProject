package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Addresses, Integer> {

    @Query("select a from Addresses a where a.addressID =?1")
    Addresses findAddressesByID(Integer id);
}
