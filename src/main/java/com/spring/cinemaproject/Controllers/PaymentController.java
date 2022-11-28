package com.spring.cinemaproject.Controllers;

import javax.servlet.http.HttpServletRequest;

import com.spring.cinemaproject.Models.*;
import com.spring.cinemaproject.Repositories.*;
import com.spring.cinemaproject.Services.TicketBookingService;
import com.spring.cinemaproject.Services.VoucherService;
import org.apache.catalina.LifecycleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.spring.cinemaproject.Config.PaypalPaymentIntent;
import com.spring.cinemaproject.Config.PaypalPaymentMethod;
import com.spring.cinemaproject.Services.PaypalService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.sql.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/Payment")
public class PaymentController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private PaypalService paypalService;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private ChairRepository chairRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketBookingService ticketBookingService;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherService voucherService;

    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";
    public static final String PAYMENT = "PAYPAL";

    public static Integer filmID = 0;
    public static  String VOUCHER = null ;
    public static Integer movieID =0 ;
    public static Integer cinemaID = 0;
    public static Date bookingDate = null;
    public static Date bookingTime = null;
    public static List<Integer> foodsID = null;
    public static List<Integer> foodsAmount = null;
    public static List<Integer> combosID = null;
    public static List<Integer> combosAmount = null;
    public static List<Integer> seatsID = null;
    public static List<Chairs> getChairs = null;
    public static List<Foods> getFoods = null;
    public static List<Combos> getCombos = null;

    public static String lang = "en";

    public static float ticketPrice = 0;
    private Logger log = LoggerFactory.getLogger(getClass());

    //http://localhost:8080/Payment/viewPrice?filmID=14&date=23-10-2022&time=07:30:00&cinema=3&seats=0,79,91,92,104
    @GetMapping("/viewPrice")
    public String viewPrice(Model model, @RequestParam(name = "filmID") Integer ID, @RequestParam(name = "date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
                            @RequestParam("time") @DateTimeFormat(pattern = "HH:mm:ss") Date time, @RequestParam(name="cinema") Integer cinema, @RequestParam(name = "seats")List<Integer> seats,
                            @RequestParam("foods") List<Integer> foods,@RequestParam("foodAmount") List<Integer> foodAmount,
                            @RequestParam("combos") List<Integer> combos,@RequestParam("comboAmount") List<Integer> comboAmount ,@RequestParam(name = "total") float total) throws IOException, ClassNotFoundException {
        //serialize to object
        filmID =ID;
        movieID = filmID;
        cinemaID= cinema;
        bookingDate= date;
        bookingTime= time;
        foodsID = foods;
        foodsAmount = foodAmount;
        combosAmount= comboAmount;
        combosID = combos;
        seatsID = seats;

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
        int memberPoint =0;
        if(user.getMemberships() !=null){
            memberPoint = user.getMemberships().getPoints();
        }

        float foodPrice = 0;
        float comboPrice = 0;
        float pointPrice = 0;

        //handle list integer data to list object
        List<Chairs> chairs = new ArrayList<>();
        for(int i =0 ; i<seats.size(); i++){
            chairs.add(chairRepository.findChairsByID(seats.get(i)));
        }
        getChairs = chairs;
        List<Foods> food = new ArrayList<>();
        for(int i =0 ; i<foods.size(); i++){
            Foods f = foodRepository.findFoodsByID(foods.get(i));
            food.add(f);
            foodPrice +=f.getFoodprice()*foodAmount.get(i);
        }
        getFoods = food;
        List<Combos> combo = new ArrayList<>();
        for(int i =0 ; i<combos.size(); i++){
            Combos c = comboRepository.findCombosByID(combos.get(i));
            combo.add(c);
            comboPrice += c.getComboprice() * comboAmount.get(i);
        }
        getCombos = combo;
        total += comboPrice + foodPrice;
        if(memberPoint == 100){
            pointPrice = total*10/100;
            ticketPrice = total - pointPrice;
        }
        else if(memberPoint != 100){
            pointPrice =0;
            ticketPrice = total;
        }
        model.addAttribute("membership",pointPrice);
//        ticketBookingService.createTicket(chairs);
        voucherService.deleteVoucher();
        List<Vouchers> vouchers = voucherRepository.findVouchersForUser(user);

        //Send Data to ViewPAge
        model.addAttribute("lang",lang);
        model.addAttribute("film", filmRepository.findFilmsByID(filmID) );
        model.addAttribute("date",date);
        model.addAttribute("time", time);
        model.addAttribute("cinema", cinemaRepository.findCinemasByID(cinema));
        model.addAttribute("chairs", chairs);
        model.addAttribute("foods", food);
        model.addAttribute("combos", combo);
        model.addAttribute("price", ticketPrice);
        model.addAttribute("foodPrice", foodPrice);
        model.addAttribute("comboPrice", comboPrice);
        model.addAttribute("vouchers",vouchers);

        return "index";
    }
    @RequestMapping("/voucher")
    public String proccessVoucher(@RequestParam("id") String id, RedirectAttributes redirectAttributes){
        VOUCHER = id;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");

        redirectAttributes.addAttribute("filmID",filmID );
        redirectAttributes.addAttribute("date",df.format(bookingDate) );
        redirectAttributes.addAttribute("time",tf.format(bookingTime) );
        redirectAttributes.addAttribute("cinema",cinemaID );
        redirectAttributes.addAttribute("seats",getChairs);
        redirectAttributes.addAttribute("foods",foodsID );
        redirectAttributes.addAttribute("foodAmount",foodsAmount );
        redirectAttributes.addAttribute("combos",combosID );
        redirectAttributes.addAttribute("comboAmount",combosAmount);

        Vouchers vouchers = voucherRepository.findVouchersByID(VOUCHER);
        if(vouchers.getAmount() > 0){
            ticketPrice -= ticketPrice*vouchers.getDiscount()/100;
            redirectAttributes.addAttribute("total",ticketPrice );
        }else{
            redirectAttributes.addAttribute("total",ticketPrice );
            return "redirect:/Payment/viewPrice";
        }
        return "redirect:/Payment/viewPrice";
    }

    @PostMapping("/pay")
    public String pay(HttpServletRequest request,@RequestParam("price") double price ){
        String cancelUrl = Utils.getBaseURL(request) + "/Payment/" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/Payment/" + URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/Films";
    }
    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(){
        return "cancel";
    }
    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,HttpServletRequest request){
        try{
            //Get User Info
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email;
            if (principal instanceof UserDetails) {
                 email = ((UserDetails)principal).getUsername();
            } else {
                 email = principal.toString();
            }
            Users user = userRepository.findByEmail(email);

            Payment payment = paypalService.executePayment(paymentId, payerId);

            ticketBookingService.createBillPayment(user,PAYMENT,VOUCHER,ticketPrice,getChairs,getFoods,getCombos);
            for(int i=0; i< getChairs.size(); i++){
                Chairs h =chairRepository.findChairsByID(getChairs.get(i).getChairID());
                h.setStatus(2);
                chairRepository.save(h);
            }
            if(user.getMemberships() != null){
                if(user.getMemberships().getPoints() ==100){
                    user.getMemberships().setPoints(0);
                    membershipRepository.save(user.getMemberships());
                }
                int bonus =user.getMemberships().getPoints() +10;
                user.getMemberships().setPoints(bonus);
                membershipRepository.save(user.getMemberships());
            }
            if(VOUCHER != null){
                Vouchers vouchers = voucherRepository.findVouchersByID(VOUCHER);
                vouchers.setAmount(vouchers.getAmount()-1);
                voucherRepository.save(vouchers);
            }

            if(payment.getState().equals("approved")){
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "redirect:/Films";
    }
}
