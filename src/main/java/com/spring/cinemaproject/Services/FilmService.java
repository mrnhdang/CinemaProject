package com.spring.cinemaproject.Services;

import com.spring.cinemaproject.Models.Films;
import com.spring.cinemaproject.Repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;

    public List<Films> searchFilm(String keyword){
        if(keyword !=null){
            return filmRepository.search(keyword);
        }
        return filmRepository.findAll();
    }
}
