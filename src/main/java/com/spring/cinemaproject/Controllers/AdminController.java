package com.spring.cinemaproject.Controllers;

import com.spring.cinemaproject.Models.*;
import com.spring.cinemaproject.Repositories.*;
import com.spring.cinemaproject.Services.BillExcelExporter;
import com.spring.cinemaproject.Services.ChairService;
import com.spring.cinemaproject.Services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ChairRepository chairRepository;
    @Autowired
    private ChairService chairService;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NewRepository newRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private EmployeeRepository employeeRepository;

    public static Integer cinemaID =0;
    public static Integer roomID =0;
    public static String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    @RequestMapping("/mainPage")
    public String AdminPage(Model model){
        Date currentDate = Calendar.getInstance().getTime();
        model.addAttribute("list_film", filmRepository.findFilmsOnSchedule(currentDate));
        return "Admin/AdminPage";
    }
    @RequestMapping("/calendar")
    public String CalendarPage(Model model){
        model.addAttribute("schedules", scheduleRepository.findAll());
        return "Admin/Calendar";
    }

    //Film Page
    @GetMapping("/film")
    public String filmMageForm(Model model){
        model.addAttribute("films", filmRepository.findAll());
        model.addAttribute("directors",directorRepository.findAll());
        model.addAttribute("producers", producerRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll() );
        return "Admin/filmManage";
    }
    @GetMapping("/film/delete")
    public String filmDeleteForm(Integer id , Model model){
        filmRepository.deleteById(id);
        return "redirect:/Admin/film";
    }
    @PostMapping(value = "/film/update")
    public String filmUpdateForm( Films film, HttpServletRequest request, @RequestParam("genre") List<Integer> genreIDs){
        try {
            Films temp = filmRepository.findFilmsByID(film.getFilmID());
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            System.out.println(fileName);
//            film.setImage(fileName);
//            String uploadDir = "./film-img/" + temp.getFilmID();
//
//            Path uploadPath = Paths.get(uploadDir);
//
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//            try(InputStream inputStream = multipartFile.getInputStream()) {
//
//
//                Path filePath =uploadPath.resolve(fileName);
//                System.out.println(filePath.toFile().getAbsolutePath());
//                Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            }catch(IOException e){
//                throw new IOException("Couldn't save upload file " + fileName);
//            }

            Integer directorID =0;
            Integer producerID = 0;
            Set<Genres> genres = new HashSet<>();
            if(request.getParameter("director") !=null){
                directorID = Integer.parseInt(request.getParameter("director"));
            }
            if(request.getParameter("producer")!= null){
                producerID = Integer.parseInt(request.getParameter("producer"));
            }
            for(int i =0; i<genreIDs.size() ; i++){
                genres.add(genreRepository.findGenresByID(genreIDs.get(i)));
            }
            if(temp != null){
                temp.setImage(film.getImage());
                temp.setRated(film.getRated());
                temp.setStatus(film.isStatus());
                temp.setFilmDescription1(film.getFilmDescription1());
                temp.setFilmDescription(film.getFilmDescription());
                temp.setFilmName1(film.getFilmName1());
                temp.setFilmName(film.getFilmName());
                temp.setUrlTrailer(film.getUrlTrailer());
                temp.setRuntime(film.getRuntime());
                temp.setPrice(film.getPrice());
                if(genreIDs.get(0) != 0) {
                    temp.setGenres(genres);
                }
                if(film.getReleaseDate() !=null ){
                    temp.setReleaseDate(film.getReleaseDate());
                }
                if(film.getEndDate()!= null){
                    temp.setEndDate(film.getEndDate());
                }
                if(directorID != 0){
                    Directors directors = directorRepository.findDirectorsByID(directorID);
                    temp.setDirectors(directors);
                }
                if(producerID!= 0){
                    Producers producers = producerRepository.findProducersByID(producerID);
                    temp.setProducers(producers);
                }
                filmRepository.save(temp);
            }
            else
            {
                filmRepository.save(film);
            }
        }catch (Exception e){

            return "redirect:/Admin/film";
        }

        return "redirect:/Admin/film";
    }
    @GetMapping("/findFilmID")
    @ResponseBody
    public Films findOne(Integer id){
        return filmRepository.findFilmsByID(id);
    }

    //Schedule
    @RequestMapping("/schedule")
    public String scheduleForm(Model model){
        scheduleService.deleteScheduleAfterEnd();

        Set<Rooms> roomsList = new HashSet<>();
        for(Schedules schedules : scheduleRepository.findAll()){
            for(Rooms rooms : roomRepository.findAll()){
                if(schedules.getRooms() != rooms){
                    roomsList.add(rooms);
                }
            }
        }

        model.addAttribute("schedules", scheduleRepository.findAll());
        model.addAttribute("cinemas",cinemaRepository.findAll());
        model.addAttribute("rooms",roomRepository.findAll());
        model.addAttribute("films", filmRepository.findAll());
        return "Admin/scheduleManage";
    }
    @GetMapping("/schedule/delete")
    public String scheduleDeleteForm(@RequestParam("id") Integer id , Model model){
        scheduleRepository.deleteById(id);
        return "redirect:/Admin/schedule";
    }
    @PostMapping(value = "/schedule/update")
    public String scheduleUpdateForm(Schedules schedule, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            Schedules temp = scheduleRepository.findsScheduleByID(schedule.getScheduleID());
            Integer filmID = 0;
            Integer roomID = 0;
            if(request.getParameter("filmID") != null){
                filmID = Integer.parseInt(request.getParameter("filmID"));
            }
            if(request.getParameter("roomID")!=null){
                roomID=Integer.parseInt(request.getParameter("roomID"));
            }
            if(temp != null){
                temp.setStatus(schedule.getStatus());
                if(roomID!=0){
                    temp.setRooms(roomRepository.findRoomsByID(roomID));
                }
                if(filmID!=0){
                    temp.setFilms(filmRepository.findFilmsByID(filmID));
                }
                if(schedule.getShowTime().before(temp.getFilms().getReleaseDate()) ==true ){
                    redirectAttributes.addFlashAttribute("message", "The show time is before the Release Date");
                    return "redirect:/Admin/schedule";
                }else{
                    temp.setShowTime(schedule.getShowTime());
                }
                if(scheduleRepository.validateSchedule(roomID, temp.getShowTime()) != null){
                    redirectAttributes.addFlashAttribute("message", "This room has already had schedule");
                    return "redirect:/Admin/schedule";
                }
                scheduleRepository.save(temp);
            }
            else
            {
                schedule.setRooms(roomRepository.findRoomsByID(roomID));
                schedule.setFilms(filmRepository.findFilmsByID(filmID));
                if(schedule.getShowTime().before(schedule.getFilms().getReleaseDate()) ==true ){
                    redirectAttributes.addFlashAttribute("message", "The show time is before the Release Date");
                    return "redirect:/Admin/schedule";
                }else{
                    schedule.setShowTime(schedule.getShowTime());
                }
                if(scheduleRepository.validateSchedule(roomID, schedule.getShowTime()) != null){
                    redirectAttributes.addFlashAttribute("message", "This room has already had schedule");
                    return "redirect:/Admin/schedule";
                }
                scheduleRepository.save(schedule);
            }
        }catch (Exception e){
            return "redirect:/Admin/schedule";
        }
        return "redirect:/Admin/schedule";
    }
    @GetMapping("/findScheduleID")
    @ResponseBody
    public Schedules findSchedule(Integer id){
        return scheduleRepository.findsScheduleByID(id);
    }

    //Cinema
    @RequestMapping("/cinema")
    public String cinemaPage(Model model){
        model.addAttribute("cinemas", cinemaRepository.findAll());
        model.addAttribute("addresses", addressRepository.findAll());
        model.addAttribute("contacts", contactRepository.findAll());
        return "Admin/cinemaManage";
    }
    @GetMapping("/cinema/delete")
    public String cinemaDelete(Integer id){
        cinemaRepository.deleteById(id);
        return "redirect:/Admin/cinema";
    }
    @PostMapping(value = "/cinema/update")
    public String cinemaUpdate(Cinemas cinemas, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            Cinemas temp = cinemaRepository.findCinemasByID(cinemas.getCinemaID());
            Integer addressID = 0;
            Integer contactID = 0;
            if(request.getParameter("addressID") !=null){
                addressID= Integer.parseInt(request.getParameter("addressID"));
            }
            if(request.getParameter("contactID") !=null){
                contactID= Integer.parseInt(request.getParameter("contactID"));
            }
            if(temp!=null){
                if(addressID !=0){
                    temp.setAddresses(addressRepository.findAddressesByID(addressID));
                }else{
                    temp.setAddresses(cinemas.getAddresses());
                }
                if(contactID !=0){
                    temp.setContacts(contactRepository.findContactsByID(contactID));
                }else{
                    temp.setContacts(cinemas.getContacts());
                }
                temp.setCinemaName(cinemas.getCinemaName());
                cinemaRepository.save(temp);
            }else{
                cinemas.setAddresses(addressRepository.findAddressesByID(addressID));
                cinemas.setContacts(contactRepository.findContactsByID(contactID));
                cinemaRepository.save(cinemas);
            }
        }catch (Exception e){
            return "redirect:/Admin/cinema";
        }
        return "redirect:/Admin/cinema";
    }
    @GetMapping("/findCinemaID")
    @ResponseBody
    public Cinemas findCinema(Integer id){
        return cinemaRepository.findCinemasByID(id);
    }

    //Create room for cinema
    @RequestMapping("/room/{id}")
    public String roomPage(@PathVariable("id") Integer id, Model model){
        cinemaID = id;
        model.addAttribute("cinema", cinemaRepository.findCinemasByID(id));
        model.addAttribute("rooms", roomRepository.findRoomsByCinema(cinemaRepository.findCinemasByID(id)));
        return "Admin/roomManage";
    }
    @GetMapping("/room/delete")
    public String roomDelete(Integer id , Model model){
        roomRepository.deleteById(id);
        return "redirect:/Admin/room/"+cinemaID;
    }
    @PostMapping(value = "/room/update")
    public String roomUpdate(Rooms rooms, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            rooms.setCinemas(cinemaRepository.findCinemasByID(cinemaID));
            roomRepository.save(rooms);
        }catch (Exception e){
            return "redirect:/Admin/room/"+cinemaID;
        }
        return "redirect:/Admin/room/"+cinemaID;
    }
    @GetMapping("/findRoomID")
    @ResponseBody
    public Rooms findRoom(Integer id){
        return roomRepository.findRoomsByID(id);
    }

    //Chairs for Room
    @RequestMapping("/seat/{id}")
    public String seatPage(@PathVariable("id") Integer id, Model model ){
        roomID=id;

        model.addAttribute("alphabet",chairService.findExistAlphabet(id));
        model.addAttribute("room", roomRepository.findRoomsByID(id));
        model.addAttribute("chairs", chairRepository.findChairsByRooms(id));
        return "Admin/seatManage";
    }
    @GetMapping("/seat/deleteAll")
    public String deleteAllSeat(@RequestParam("id") Integer id){
        chairService.deleteAllRoomChair(id);
        return "redirect:/Admin/seat/"+id;
    }
//    @GetMapping(value = "/seat/delete/{id}")
//    @ResponseBody
//    ResponseEntity<ResponseObject> deleteProduct(@PathVariable("id") Integer id){
//        boolean exists =chairRepository.existsById(id);
//        if(exists){
//            chairRepository.deleteById(id);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok","Delete product successfully","")
//            );
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                new ResponseObject("Failed","Cannot find product to delete","")
//        );
//    }
    @GetMapping("/seat/updateSelect")
    public String updateSelected(@RequestParam("list") List<Integer> list, @RequestParam("id") Integer roomID ){
        for(Integer item : list){
            Chairs chairs= chairRepository.findChairsByID(item);
            if(chairs.getStatus() == 0){
                chairs.setStatus(2);
            }else{
                chairs.setStatus(0);
            }
            chairRepository.save(chairs);

        }
        return "redirect:/Admin/seat/"+roomID;
    }

    @PostMapping("/seat/create")
    public String seatUpdate(HttpServletRequest request){
        String rowName= request.getParameter("rowName");
        Integer column = Integer.parseInt(request.getParameter("column"));

        chairService.createContinueChair(rowName, column, roomID);
        return "redirect:/Admin/seat/"+roomID;
    }

    @PostMapping("/seat/createAll")
    public String createAllChairs(HttpServletRequest request){

        Integer row = Integer.parseInt(request.getParameter("row"));
        Integer chairNum = Integer.parseInt(request.getParameter("chairNum"));
        String eachRow ;
        int countRow =0;

        List<Chairs> chairsList = chairRepository.findChairsByRooms(roomID);
        Set<String> set = new HashSet<String>();

        for(int i=0; i< alphabet.length ; i++) {
            for (Chairs item : chairsList) {
                if (item.getChairName().contains(alphabet[i]) == true) {
                    set.add(alphabet[i]);
                }
            }
        }
        for(int i=0; i<row ; i++){
            countRow = set.size();
            int temp = countRow;
            temp += i ;
            eachRow=alphabet[temp];
            chairService.createContinueChair(eachRow , chairNum, roomID);
        }
        return "redirect:/Admin/seat/"+roomID;
    }

    //Bill Payment
    @GetMapping("/bill")
    public String billPage(Model model){
        model.addAttribute("bills", billRepository.findAll());
        return "Admin/billManage";
    }

    @GetMapping("/bill/update")
    public String updateStatus(@RequestParam("id") Integer billID){
        Bills bills =billRepository.findBillsByID(billID);
        if(bills.getStatus()==1){
            bills.setStatus(2);
        }else
        {
            bills.setStatus(1);
        }
        billRepository.save(bills);
        return "redirect:/Admin/bill";
    }
    @GetMapping("/bill/export")
    public String exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerkey  ="Content-Disposition";

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        String filename ="cinemabills_"+currentDate+".xlsx";

        String headerValue= "attachement; filename="+filename;

        response.setHeader(headerkey,headerValue);

        List<Bills> bill = billRepository.findAll();
        BillExcelExporter billExcelExporter = new BillExcelExporter(bill);
        billExcelExporter.export(response);

        return "Admin/billManage";
    }

    //Voucher
    @RequestMapping("/voucher")
    public String voucherPage(Model model){
        model.addAttribute("users", userRepository.findAll() );
        model.addAttribute("vouchers",  voucherRepository.findAll());
        return "Admin/voucherManage";
    }
    @GetMapping("/voucher/delete")
    public String deleteVoucher(String id){
        voucherRepository.deleteById(id);
        return "redirect:/Admin/voucher";
    }
    @PostMapping(value = "/voucher/update")
    public String scheduleUpdateForm(Vouchers vouchers, HttpServletRequest request, RedirectAttributes redirectAttributes){
//        try {
            Vouchers temp = voucherRepository.findVouchersByID(vouchers.getVoucherID());
            if(temp != null){
                temp.setVoucherName(vouchers.getVoucherName());
                temp.setVoucherName1(vouchers.getVoucherName1());
                temp.setDiscount(vouchers.getDiscount());
                temp.setAmount(vouchers.getAmount());
                if(vouchers.getEffectiveDate() != null && vouchers.getExpireDate()!= null){
                    if(vouchers.getExpireDate().before(vouchers.getEffectiveDate()) ==true ){
                        redirectAttributes.addFlashAttribute("message", "The Effective time must be before the expire Date");
                        return "redirect:/Admin/voucher";
                    }else{
                        temp.setEffectiveDate(vouchers.getEffectiveDate());
                        temp.setExpireDate(vouchers.getExpireDate());
                    }
                }
                if (request.getParameter("user") != null){
                    Users userVoucher = userRepository.findByEmail(request.getParameter("user"));
                    temp.setUsers(userVoucher);
                }
                voucherRepository.save(temp);
            }
            else
            {
                if(vouchers.getEffectiveDate() != null && vouchers.getExpireDate()!= null) {
                    if (vouchers.getExpireDate().before(vouchers.getEffectiveDate()) == true) {
                        redirectAttributes.addFlashAttribute("message", "The Effective time must be before the expire Date");
                        return "redirect:/Admin/voucher";
                    }
                }
                if (request.getParameter("user") != null){
                    Users userVoucher = userRepository.findByEmail(request.getParameter("user"));
                    vouchers.setUsers(userVoucher);
                }
                voucherRepository.save(vouchers);
            }
//        }catch (Exception e){
//            return "redirect:/Admin/voucher";
//        }
        return "redirect:/Admin/voucher";
    }
    @GetMapping("/findVoucher")
    @ResponseBody
    public Vouchers findOne(String id){
        return voucherRepository.findVouchersByID(id);
    }

    //News
    @RequestMapping("/new")
    public String newPage(Model model){
        model.addAttribute("news", newRepository.findAll());
        return "Admin/newManage";
    }
    @GetMapping("/new/delete")
    public String deleteNew(Integer id){
        newRepository.deleteById(id);
        return "redirect:/Admin/new";
    }
    @PostMapping(value = "/new/update")
    public String newUpdate(News news, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
        News temp = newRepository.findNewsByID(news.getNewID());
        if(temp != null){
            temp.setImage(news.getImage());
            temp.setDescription(news.getDescription());
            temp.setNewName(news.getNewName());
            temp.setNewName1(news.getNewName1());

            newRepository.save(temp);
        }
        else
        {
            newRepository.save(news);
        }
        }catch (Exception e){
            return "redirect:/Admin/new";
        }
        return "redirect:/Admin/new";
    }
    @GetMapping("/findNew")
    @ResponseBody
    public News findNew(Integer id){
        return newRepository.findNewsByID(id) ;
    }
    //Food
    @RequestMapping("/food")
    public String foodPage(Model model){
        model.addAttribute("foods",foodRepository.findAll());
        return "Admin/foodManage";
    }
    @GetMapping("/food/delete")
    public String deleteFood(Integer id){
        foodRepository.deleteById(id);
        return "redirect:/Admin/food";
    }
    @PostMapping(value = "/food/update")
    public String newUpdate(Foods foods, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            Foods temp = foodRepository.findFoodsByID(foods.getFoodID());
            if(temp != null){
                temp.setFoodName(foods.getFoodName());
                temp.setFoodName1(foods.getFoodName1());
                temp.setFoodprice(foods.getFoodprice());
                temp.setImage(foods.getImage());

                foodRepository.save(temp);
            }
            else
            {
                foodRepository.save(foods);
            }
        }catch (Exception e){
            return "redirect:/Admin/food";
        }
        return "redirect:/Admin/food";
    }
    @GetMapping("/findFood")
    @ResponseBody
    public Foods findFood(Integer id){
        return foodRepository.findFoodsByID(id) ;
    }
    //Combo
    @RequestMapping("/combo")
    public String comboPage(Model model){
        model.addAttribute("combos", comboRepository.findAll());
        return "Admin/comboManage";
    }
    @GetMapping("/combo/delete")
    public String deleteCombo(Integer id){
        comboRepository.deleteById(id);
        return "redirect:/Admin/combo";
    }
    @PostMapping(value = "/combo/update")
    public String comboUpdate(Combos combos, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            Combos temp = comboRepository.findCombosByID(combos.getComboID());
            if(temp != null){
                temp.setComboID(combos.getComboID());
                temp.setComboName1(combos.getComboName1());
                temp.setComboprice(combos.getComboprice());
                temp.setImage(combos.getImage());

               comboRepository.save(temp);
            }
            else
            {
                comboRepository.save(combos);
            }
        }catch (Exception e){
            return "redirect:/Admin/combo";
        }
        return "redirect:/Admin/combo";
    }
    @GetMapping("/findCombo")
    @ResponseBody
    public Combos findComo(Integer id){
        return comboRepository.findCombosByID(id);
    }
    //USER
    @GetMapping("/user")
    public String viewUserList(Model model){
        List<Users> list= userRepository.findAll();
        List<Employees> list1 = employeeRepository.findAll();
        model.addAttribute("employees", list1);
        model.addAttribute("users", list);
        return "Admin/userManage";
    }
    @GetMapping("/user/delete")
    public String deleteUser(Integer id){
        userRepository.deleteById(id);
        return "redirect:/Admin/user";
    }
    @PostMapping(value = "/user/update")
    public String userUpdate(Users user, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            Users temp = userRepository.findUsersByID(user.getUserID());
            if(temp != null){
                if(request.getParameter("employee") !=null){
                    Set<Employees> set = new HashSet<>();
                    Employees em =  employeeRepository.findEmployeesByEmployeeName(request.getParameter("employee"));
                    set.add(em);
                    temp.setEmployees(set);
                }
               userRepository.save(temp);
            }
        }catch (Exception e){
            return "redirect:/Admin/user";
        }
        return "redirect:/Admin/user";
    }
    @GetMapping("/findUser")
    @ResponseBody
    public Users findUSer(String id){
        return userRepository.findUsersByID(id) ;
    }
    //Address
    @GetMapping("/address")
    public String addressPage(Model model){
        model.addAttribute("addresses", addressRepository.findAll());
        return "Admin/addressManage";
    }
    @GetMapping("/address/delete")
    public String deleteAddress(Integer id){
        addressRepository.deleteById(id);
        return "redirect:/Admin/address";
    }
    @PostMapping(value = "/address/update")
    public String userUpdate(Addresses addresses, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            Addresses temp = addressRepository.findAddressesByID(addresses.getAddressID());
            if(temp != null){
               temp.setAddressName(addresses.getAddressName());
               temp.setAddressName1(addresses.getAddressName1());
                addressRepository.save(temp);
            }
            else{
                addressRepository.save(addresses);
            }
        }catch (Exception e){
            return "redirect:/Admin/address";
        }
        return "redirect:/Admin/address";
    }
    @GetMapping("/findAddress")
    @ResponseBody
    public Addresses findAddress(Integer id){
        return addressRepository.findAddressesByID(id) ;
    }
    //Contact
    @GetMapping("/contact")
    public String contactPage(Model model){
        model.addAttribute("contacts", contactRepository.findAll());
        return "Admin/contactManage";
    }
    @GetMapping("/contact/delete")
    public String deleteContact(Integer id){
        contactRepository.deleteById(id);
        return "redirect:/Admin/contact";
    }
    @PostMapping(value = "/contact/update")
    public String updateContact(Contacts contacts, HttpServletRequest request, RedirectAttributes redirectAttributes){
        try {
            Contacts temp = contactRepository.findContactsByID(contacts.getContactID());
            if(temp != null){
              temp.setContactName(contacts.getContactName());
              temp.setContactName1(contacts.getContactName1());
              contactRepository.save(temp);
            }
            else{
               contactRepository.save(contacts);
            }
        }catch (Exception e){
            return "redirect:/Admin/contact";
        }
        return "redirect:/Admin/contact";
    }
    @GetMapping("/findContact")
    @ResponseBody
    public Contacts findContact(Integer id){
        return contactRepository.findContactsByID(id) ;
    }
}