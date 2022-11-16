package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewRepository extends JpaRepository<News, Integer> {

    @Query("select n from News n where n.newID =?1")
    News findNewsByID(Integer id);
}
