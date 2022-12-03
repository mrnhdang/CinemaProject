package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Memberships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MembershipRepository extends JpaRepository<Memberships, Integer> {
    @Query("select m from Memberships m where m.users.userID =?1")
    Memberships findMembershipsByUsersId(String id);
}
