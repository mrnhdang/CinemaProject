package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactRepository extends JpaRepository<Contacts, Integer> {
    @Query("select a from Contacts a where a.contactID =?1")
    Contacts findContactsByID(Integer id);
}
