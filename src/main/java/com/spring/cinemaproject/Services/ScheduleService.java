package com.spring.cinemaproject.Services;

import com.spring.cinemaproject.Models.Films;
import com.spring.cinemaproject.Models.Rooms;
import com.spring.cinemaproject.Models.Schedules;
import com.spring.cinemaproject.Repositories.FilmRepository;
import com.spring.cinemaproject.Repositories.ScheduleRepository;
import com.spring.cinemaproject.Repositories.TicketRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.*;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ChairService chairService;
    @Autowired
    private TicketRepository ticketRepository;

    public void deleteScheduleAfterEnd(){
        try{
            for (Schedules schedules : scheduleRepository.findAll()){
                for(Films films : filmRepository.findAll()){
                    Date currentDate = Calendar.getInstance().getTime();
                    if(schedules.getFilms() == films){
                        Date showtime = DateUtils.addMinutes(schedules.getShowTime(),films.getRuntime());
                        if( currentDate.after(showtime) == true ){
                            chairService.setChairDefault(schedules.getRooms());
                            scheduleRepository.delete(schedules);
                        }
                    }

                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public boolean validateSchedule(Films films, Rooms rooms, Date validDate){
        List<Schedules> validSchedule = scheduleRepository.findScheduleByRoomAndFilm(rooms);
        for(Schedules schedule : validSchedule){
            Date runtime = DateUtils.addMinutes(schedule.getShowTime(),schedule.getFilms().getRuntime());
            if(validDate.after(schedule.getShowTime()) && validDate.before(runtime) || validDate.equals(schedule.getShowTime()) || validDate.equals(runtime) ){
                return true;
            }
        }
        return false;

    }

}
