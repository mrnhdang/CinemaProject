package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Tickets,Integer> {
}
