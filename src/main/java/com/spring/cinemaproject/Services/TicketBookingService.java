package com.spring.cinemaproject.Services;

import com.spring.cinemaproject.Models.*;
import com.spring.cinemaproject.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class TicketBookingService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private FilmRepository filmRepository;

    @Transactional
    public void createBillPayment(Users user, String payment, String voucher , float total, List<Chairs> chairs, List<Foods> foods, List<Combos> combos, Integer filmID) throws Exception {
        Bills ticketBill = new Bills();
        Tickets ticket = new Tickets();
        List<Tickets> tickets = new ArrayList<>();
        List<Bills> bills = new ArrayList<>();
        try {
            List<String> note = new ArrayList<>();
            for(Foods food : foods){
                note.add(food.getFoodName());
            }
            for(Combos combo : combos){
                note.add(combo.getComboName());
            }
            //create ticket
            Set<Chairs> chairsSet = new HashSet<>(chairs);
            Set<Foods> foodSet = new HashSet<>(foods);
            Set<Combos> comboSet = new HashSet<>(combos);

            ticket.setCreate_date(Calendar.getInstance().getTime());
            ticket.setChairs(chairsSet);
            tickets.add(ticket);
            ticketRepository.save(ticket);
            Set<Tickets> ticketsSet = new HashSet<>(tickets);
            //create Bill
            ticketBill.setUsers(user);
            ticketBill.setCreateDate(Calendar.getInstance().getTime());
            ticketBill.setPayments(paymentRepository.findPaymentsByName(payment));
            if(voucher!=null){
                ticketBill.setVouchers(voucherRepository.findVouchersByID(voucher));
            }
            ticketBill.setTickets(ticketsSet);
            ticketBill.setFilmName(filmRepository.findFilmsByID(filmID).getFilmName());
            ticketBill.setBillTotal(total);
            ticketBill.setStatus(1);
            ticketBill.setNote(note.toString());
            bills.add(ticketBill);
            //save Bills into DB
            billRepository.save(ticketBill);

        }catch (Exception e){
            throw new Exception("Something is wrong ...... " + e);
        }
    }
}
