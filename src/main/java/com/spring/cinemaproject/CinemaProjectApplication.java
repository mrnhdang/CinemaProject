package com.spring.cinemaproject;

import com.spring.cinemaproject.Services.ScheduleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class  CinemaProjectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(CinemaProjectApplication.class, args);
        context.getBean(ScheduleService.class).deleteScheduleAfterEnd();
    }

}
