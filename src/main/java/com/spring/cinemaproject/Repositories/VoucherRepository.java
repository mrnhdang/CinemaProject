package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Users;
import com.spring.cinemaproject.Models.Vouchers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface VoucherRepository extends JpaRepository<Vouchers,String> {
    @Query("select v from Vouchers v where v.users = ?1 or v.users is null and v.amount > 0 ")
    List<Vouchers> findVouchersForUser(Users users);

    @Query("select v from Vouchers v where v.voucherID = ?1")
    Vouchers findVouchersByID(String id);

}
