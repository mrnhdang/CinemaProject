package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Tickets,Integer> {
    @Query("select f from Tickets f where f.ticketID=?1")
    Tickets findTicketsByID(Integer id);
}
