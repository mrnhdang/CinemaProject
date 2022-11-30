package com.spring.cinemaproject.Services;

import com.spring.cinemaproject.Models.Films;
import com.spring.cinemaproject.Repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Transactional
@Service
public class FilmService  {
    @Autowired
    FilmRepository filmRepository;

    public Page<Films> searchPaginated(String keyword, Pageable pageable) {
        return filmRepository.searchPaginated(keyword, pageable);
    }

    public List<Films> searchFilm(String keyword){
        if(keyword !=null){
            return filmRepository.search(keyword);
        }
        return filmRepository.findAll();
    }
}
