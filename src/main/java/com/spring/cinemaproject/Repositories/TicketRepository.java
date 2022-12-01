package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Schedules;
import com.spring.cinemaproject.Models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Tickets,Integer> {
}
