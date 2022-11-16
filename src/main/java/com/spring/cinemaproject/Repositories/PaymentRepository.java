package com.spring.cinemaproject.Repositories;


import com.spring.cinemaproject.Models.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payments,Integer> {
    @Query("select  f from Payments f where f.paymentName=?1")
    Payments findPaymentsByName(String name);
}
