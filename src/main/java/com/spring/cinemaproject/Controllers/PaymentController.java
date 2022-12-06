package com.spring.cinemaproject.Controllers;

import javax.servlet.http.HttpServletRequest;

import com.spring.cinemaproject.Models.*;
import com.spring.cinemaproject.Repositories.*;
import com.spring.cinemaproject.Services.TicketBookingService;
import com.spring.cinemaproject.Services.UserService;
import com.spring.cinemaproject.Services.VoucherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.spring.cinemaproject.Config.PaypalPaymentIntent;
import com.spring.cinemaproject.Config.PaypalPaymentMethod;
import com.spring.cinemaproject.Services.PaypalService;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Payment")
public class PaymentController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private PaypalService paypalService;
    @Autowired
    private ChairRepository chairRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private TicketBookingService ticketBookingService;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomRepository roomRepository;

    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";
    public static final String PAYMENT = "PAYPAL";

    public static Integer filmID = 0;
    public static  String VOUCHER = null ;
    public static Integer movieID =0 ;
    public static Integer roomID = 0;
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
                            @RequestParam("time") @DateTimeFormat(pattern = "HH:mm:ss") Date time, @RequestParam(name="room") Integer room, @RequestParam(name = "seats")List<Integer> seats,
                            @RequestParam("foods") List<Integer> foods,@RequestParam("foodAmount") List<Integer> foodAmount,
                            @RequestParam("combos") List<Integer> combos,@RequestParam("comboAmount") List<Integer> comboAmount ,@RequestParam(name = "total") float total) throws IOException, ClassNotFoundException {
        //serialize to object
        filmID =ID;
        movieID = filmID;
        roomID= room;
        bookingDate= date;
        bookingTime= time;
        foodsID = foods;
        foodsAmount = foodAmount;
        combosAmount= comboAmount;
        combosID = combos;
        seatsID = seats;

        Users user = userService.getCurrentUser();
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
        if(memberPoint >= 100){
            pointPrice = total*10/100;
            ticketPrice = total - pointPrice;
        }
        else if(memberPoint < 100){
            pointPrice =0;
            ticketPrice = total;
        }
        model.addAttribute("membership",pointPrice);
        voucherService.deleteVoucher();
        List<Vouchers> vouchers = voucherRepository.findVouchersForUser(user);

        //Send Data to ViewPAge
        model.addAttribute("lang",lang);
        model.addAttribute("film", filmRepository.findFilmsByID(filmID) );
        model.addAttribute("date",date);
        model.addAttribute("time", time);
        model.addAttribute("cinema", roomRepository.findRoomsByID(room).getCinemas());
        model.addAttribute("room", roomRepository.findRoomsByID(room));
        model.addAttribute("chairs", chairs);
        model.addAttribute("foods", food);
        model.addAttribute("combos", combo);
        model.addAttribute("price", ticketPrice);
        model.addAttribute("foodPrice", foodPrice);
        model.addAttribute("comboPrice", comboPrice);
        model.addAttribute("vouchers",vouchers);

        return "index";
    }
    @PostMapping("/pay")
    public String pay(HttpServletRequest request,@RequestParam("price") float price ){
        ticketPrice = price;
        if(request.getParameter("voucher") !=null){
            VOUCHER = request.getParameter("voucher");
        }
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
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try{
            //Get User Info
            Users user = userService.getCurrentUser();

            Payment payment = paypalService.executePayment(paymentId, payerId);

            for(int i=0; i< getChairs.size(); i++){
                Chairs h =chairRepository.findChairsByID(getChairs.get(i).getChairID());
                h.setStatus(2);
                chairRepository.save(h);
            }
            if(user.getMemberships() != null){
                if(user.getMemberships().getPoints() >=100){
                    user.getMemberships().setPoints(user.getMemberships().getPoints()-100);
                    membershipRepository.save(user.getMemberships());
                }
                int bonus = user.getMemberships().getPoints() + Math.round(ticketPrice/10000);
                user.getMemberships().setPoints(bonus);
                membershipRepository.save(user.getMemberships());
            }
            if(voucherRepository.findVouchersByID(VOUCHER) != null){
                Vouchers vouchers = voucherRepository.findVouchersByID(VOUCHER);
                vouchers.setAmount(vouchers.getAmount()-1);
                voucherRepository.save(vouchers);
            }
            if(payment.getState().equals("approved")){
                ticketBookingService.createBillPayment(user,PAYMENT,VOUCHER,ticketPrice,getChairs,getFoods,getCombos,filmID);
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
