package com.spring.cinemaproject.Services;

import com.spring.cinemaproject.Models.Vouchers;
import com.spring.cinemaproject.Repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.*;

@Service
@Transactional
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public void deleteVoucher(){
        Date currentDate = Calendar.getInstance().getTime();
        for(Vouchers item : voucherRepository.findAll()){
            if(item.getExpireDate().before(currentDate) == true){
                voucherRepository.delete(item);
            }
        }
    }
}
