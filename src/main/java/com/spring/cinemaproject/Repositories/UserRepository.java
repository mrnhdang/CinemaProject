package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Films;
import com.spring.cinemaproject.Models.Users;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Users, String> {
    @Query("select u from Users u where u.email=?1")
    Users findByEmail(String name);

    @Query("UPDATE Users u set u.status = true where u.userID =?1")
    @Modifying
    public void enable(String id);

    @Query("select u from Users  u where u.verificationCode = ?1")
    public Users findVertificationCode(String code);

    @Query("select u from Users u where u.userID = ?1")
    Users findUsersByID(String id);

    @Query("SELECT f FROM Users f WHERE CONCAT(f.address,' ',f.email,' ',f.createDate,' ',f.userName) LIKE %?1%")
    Page<Users> searchPaginated(String keyword, Pageable pageable);
}
