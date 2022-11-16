package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Directors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DirectorRepository extends JpaRepository<Directors, Integer> {
    @Query("select d from Directors d where d.directorID=?1")
    Directors findDirectorsByID(Integer id);
}
