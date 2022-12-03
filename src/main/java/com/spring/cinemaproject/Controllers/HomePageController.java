package com.spring.cinemaproject.Controllers;

import com.spring.cinemaproject.Models.*;

import com.spring.cinemaproject.Repositories.*;
import com.spring.cinemaproject.Services.ChairService;
import com.spring.cinemaproject.Services.ScheduleService;
import com.spring.cinemaproject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

@Controller
@RequestMapping("/Films")
public class HomePageController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ChairRepository chairRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ChairService chairService;
    @Autowired
    private NewRepository newRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;

    public static String lang = "en";

    @GetMapping("")
    public String homepage(Model model, HttpServletRequest request){
        try{
            lang= request.getParameter("lang");
            model.addAttribute("lang", lang);

            Date currentDate = Calendar.getInstance().getTime();
            model.addAttribute("list_films", filmRepository.findFilmsOnSchedule(currentDate));

        }catch (Exception ex){
            ex.printStackTrace();
            return "Film/HomPage";
        }
        return "Film/HomePage";
    }
    @GetMapping("/upcomingFilm")
    public String upcomingFilm(Model model  ){
        model.addAttribute("list_films", filmRepository.findFilmsNotSchedule());
        return "Film/upcoming";
    }

    @GetMapping(value = "/{id}")
    public String formFilmDetail(@PathVariable Integer id, Model model, HttpServletRequest request) {
        try{
            lang= request.getParameter("lang");
            model.addAttribute("lang", lang);

            Films selectedFilm = filmRepository.findFilmsByID(id);
            model.addAttribute("film_detail",selectedFilm);
            model.addAttribute("genres", selectedFilm.getGenres());
        }catch(Exception ex){
            return "redirect:/Films";
        }
        return "Film/detail";
    }
    @RequestMapping(value="/{id}/schedule")
    public String formSchedule(@PathVariable("id") Integer id ,Model model, HttpServletRequest request){
        Users user = userService.getCurrentUser();
        if(user == null){
            return "redirect:/login";
        }
        lang = request.getParameter("lang");
        model.addAttribute("lang", lang);
        scheduleService.deleteScheduleAfterEnd();

        List<Schedules> t = scheduleRepository.findAllScheduleByFilmID(id);

        Films selectedFilm = filmRepository.findFilmsByID(id);

        Set<Cinemas> film_cinema = scheduleRepository.findCinemaByFilm(selectedFilm);
        Set<String> date = new HashSet<>();
        for(Schedules item : t){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            date.add(formatter.format(item.getShowTime()));
        }

        //find film by id
        model.addAttribute("film_detail", selectedFilm);
        //find film cinema
        model.addAttribute("film_cinema",film_cinema);
        //find film room
        model.addAttribute("film_date",date);
        //get film schedules
        model.addAttribute("schedule",t);

        return "Film/BuyTicket";
    }

    @RequestMapping(value = "/Seat")
    public String formSeat(Model model, @RequestParam("filmID") Integer filmID, @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date idDate,
                          @RequestParam("time") @DateTimeFormat(pattern = "HH:mm:ss") Date idTime, @RequestParam("room") Integer idRoom){

        Films selectedFilm = filmRepository.findFilmsByID(filmID);
        Schedules selectedSchedule = scheduleRepository.findScheduleByRoomAndFilmAndDate(roomRepository.findRoomsByID(idRoom),selectedFilm,idDate);
        List<Chairs> getChairs=chairRepository.findChairsByRooms(selectedSchedule.getRooms().getRoomID());

        model.addAttribute("alphabet",chairService.findExistAlphabet(selectedSchedule.getRooms().getRoomID()));
        model.addAttribute("film", selectedFilm);
        model.addAttribute("idDate",idDate);
        model.addAttribute("idTime", idTime);
        model.addAttribute("idRoom", idRoom);
        model.addAttribute("schedule", selectedSchedule);

        model.addAttribute("chairs", getChairs );


        return "Film/Seat";
    }
    @RequestMapping("/Food")
    public String formFoodAndCombo(Model model, @RequestParam("filmID") Integer filmID, @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date idDate,
                                   @RequestParam("time") @DateTimeFormat(pattern = "HH:mm:ss") Date idTime, @RequestParam("room") Integer idRoom, @RequestParam(name = "seats")List<Integer> seats,
                                   @RequestParam(name = "total") float total, HttpServletRequest request){
        try{
            lang= request.getParameter("lang");
            model.addAttribute("lang", lang);
            List<Chairs> chairs = new ArrayList<>();
            for(int i =0 ; i<seats.size(); i++){
                chairs.add(chairRepository.findChairsByID(seats.get(i)));
            }
            model.addAttribute("film",filmRepository.findFilmsByID(filmID));
            model.addAttribute("idDate",idDate);
            model.addAttribute("idTime", idTime);
            model.addAttribute("idRoom", idRoom);
            model.addAttribute("total", total);
            model.addAttribute("chairs", chairs);

            model.addAttribute("foods",foodRepository.findAll());
            model.addAttribute("combos", comboRepository.findAll());
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/Films";
        }
        return "Film/food";
    }
    @RequestMapping("/new")
    public String newPage(Model model){
        model.addAttribute("news", newRepository.findAll());
        return "Film/new";
    }


}
