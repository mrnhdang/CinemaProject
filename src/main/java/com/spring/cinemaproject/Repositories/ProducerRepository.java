package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Producers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProducerRepository extends JpaRepository<Producers, Integer> {
    @Query("select p from Producers p where p.producerID = ?1")
    Producers findProducersByID(Integer id);
}
