package com.spring.cinemaproject.Controllers;

import com.spring.cinemaproject.Models.*;

import com.spring.cinemaproject.Repositories.*;
import com.spring.cinemaproject.Services.ChairService;
import com.spring.cinemaproject.Services.ScheduleService;
import org.hibernate.persister.entity.Loadable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/Films")
public class HomePageController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ChairRepository chairRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChairService chairService;
    @Autowired
    private NewRepository newRepository;
    @Autowired
    private ScheduleService scheduleService;

    public static String lang = "en";

    @RequestMapping(value = "/template")
    public String index(){
        return "home";
    }

    @GetMapping("")
    public String homepage(Model model, HttpServletRequest request){

        lang= request.getParameter("lang");
        model.addAttribute("lang", lang);

        Date currentDate = Calendar.getInstance().getTime();
        model.addAttribute("list_films", filmRepository.findFilmsOnSchedule(currentDate));

        return "Film/HomePage";
    }
    @GetMapping("/upcomingFilm")
    public String upcomingFilm(Model model, HttpServletRequest request){
        model.addAttribute("list_films", filmRepository.findAll());
        return "Film/upcoming";
    }

    @GetMapping(value = "/{id}")
    public String formFilmDetail(@PathVariable Integer id, Model model, HttpServletRequest request) {

        lang= request.getParameter("lang");
        model.addAttribute("lang", lang);

        Films selectedFilm = filmRepository.findFilmsByID(id);
        model.addAttribute("film_detail",selectedFilm);
        model.addAttribute("genres", selectedFilm.getGenres());
        return "Film/detail";
    }
    @RequestMapping(value="/{id}/schedule")
    public String formSchedule(@PathVariable("id") Integer id ,Model model, HttpServletRequest request){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        Users user = userRepository.findByEmail(email);
        if(user == null){
            return "redirect:/login";
        }
        lang = request.getParameter("lang");
        model.addAttribute("lang", lang);
//        Date date = new Date();
//        date = Calendar.getInstance().getTime();
//        SimpleDateFormat df  = new SimpleDateFormat("DD/MM/YY");
//        Calendar c1 = Calendar.getInstance();
//        String currentDate = df.format(date);// get current date here
//
//        List<Date> listDate= new ArrayList<>();
//        listDate.add(date);
//        // now add 30 day in Calendar instance
//        for(int i = 0 ; i<30 ; i++) {
//            c1.add(Calendar.DAY_OF_YEAR, 1);
//
//            Date resultDate = c1.getTime();
//
//            SimpleDateFormat df1  = new SimpleDateFormat("DD/MM/YY");
//            String dueDate = df1.format(resultDate);
//
//            listDate.add(resultDate);
//        }

        scheduleService.deleteScheduleAfterEnd();

        List<Schedules> t = scheduleRepository.findAllScheduleByFilmID(id);

        Films selectedFilm = filmRepository.findFilmsByID(id);

        Set<Cinemas> film_cinema = scheduleRepository.findCinemaByFilm(selectedFilm);


        //find film by id
        model.addAttribute("film_detail", selectedFilm);
        //find film cinema
        model.addAttribute("film_cinema",film_cinema);
        //get film schedules
        model.addAttribute("schedule",t);

        return "Film/BuyTicket";
    }

    /// http://localhost:8080/Films/14/schedule/Seat?date=2001-10-23&time=16:30:00&cinema=2
//    @RequestMapping(value = "/{filmID}/schedule/Seat?date={idDate}&time={idTime}&cinema={idCinema}",method = RequestMethod.GET)
//    public String formSet(Model model, @PathVariable("filmID") Integer id,
//                          @PathVariable("idDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date idDate,
//                          @PathVariable("idTime") Time idTime, @PathVariable("idCinema") Integer idCinema ){
//        return "Film/Seat";
//    }

    @RequestMapping(value = "/Seat")
    public String formSeat(Model model, @RequestParam("filmID") Integer filmID, @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date idDate,
                          @RequestParam("time") @DateTimeFormat(pattern = "HH:mm:ss") Date idTime, @RequestParam("cinema") Integer idCinema){

        String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        Set<String> alphabetExist = new HashSet<String>() ;

        Films selectedFilm = filmRepository.findFilmsByID(filmID);
        Schedules selectedSchedule = scheduleRepository.finScheduleByTimeAndFilmID(idDate ,filmID);
        List<Chairs> getChairs=chairRepository.findChairsByRooms(selectedSchedule.getRooms().getRoomID());

        model.addAttribute("alphabet",chairService.findExistAlphabet(selectedSchedule.getRooms().getRoomID()));
        model.addAttribute("film", selectedFilm);
        model.addAttribute("idDate",idDate);
        model.addAttribute("idTime", idTime);
        model.addAttribute("idCinema", idCinema);

        model.addAttribute("chairs", getChairs );


        return "Film/Seat";
    }
    @RequestMapping("/Food")
    public String formFoodAndCombo(Model model, @RequestParam("filmID") Integer filmID, @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date idDate,
                                   @RequestParam("time") @DateTimeFormat(pattern = "HH:mm:ss") Date idTime, @RequestParam("cinema") Integer idCinema, @RequestParam(name = "seats")List<Integer> seats,
                                   @RequestParam(name = "total") float total, HttpServletRequest request, RedirectAttributes redirectAttributes){

        lang= request.getParameter("lang");
        model.addAttribute("lang", lang);
        List<Chairs> chairs = new ArrayList<>();
        for(int i =0 ; i<seats.size(); i++){
            chairs.add(chairRepository.findChairsByID(seats.get(i)));
        }
        model.addAttribute("film",filmRepository.findFilmsByID(filmID));
        model.addAttribute("idDate",idDate);
        model.addAttribute("idTime", idTime);
        model.addAttribute("idCinema", idCinema);
        model.addAttribute("total", total);
        model.addAttribute("chairs", chairs);

        model.addAttribute("foods",foodRepository.findAll());
        model.addAttribute("combos", comboRepository.findAll());
        return "Film/food";
    }
    @RequestMapping("/new")
    public String newPage(Model model){
        model.addAttribute("news", newRepository.findAll());
        return "Film/new";
    }


}
