package com.spring.cinemaproject;

import static org.assertj.core.api.Assertions.*;

import com.spring.cinemaproject.Models.Chairs;
import com.spring.cinemaproject.Models.Users;
import com.spring.cinemaproject.Repositories.ChairRepository;
import com.spring.cinemaproject.Repositories.UserRepository;
import com.spring.cinemaproject.Services.TicketBookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ChairRepository chairRepository;
    @Autowired
    private TicketBookingService ticketBookingService;

    @Test
    public void testCreateUser(){
        Users user = userRepository.findByEmail("huudang23102001@gmail.com");
        user.setStatus(true);
//        user.setCreateTime("23/10/2001");

        Users savedUser= userRepository.save(user);

//        Users existUser =entityManager.find(Users.class,savedUser.getUserID());
//
//        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }
    @Test
    public void createChair(){
        for(int i = 1 ; i<=12; i++){
            Chairs chair = new Chairs();
            chair.setChairName("H" + i);
            Chairs savedChair = chairRepository.save(chair);
            Chairs exitChair = entityManager.find(Chairs.class, savedChair.getChairID());
            assertThat(exitChair.getChairName()).isEqualTo(chair.getChairName());
        }
    }
    @Test
    public void testFindUserByEmail(){
        String email="huudang23102001@gmail.com";
        Users user =userRepository.findByEmail(email);

        assertThat(user).isNotNull();
    }
    @Test
    public void findChairName(){
        System.out.print(chairRepository.getById(14));
    }

    public static void main(String[] args) {
        System.out.print( Calendar.getInstance().getTime().toString());
    }

}
