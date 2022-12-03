package com.spring.cinemaproject.Services;

import com.spring.cinemaproject.Models.Employees;
import com.spring.cinemaproject.Models.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

public class CustomUserDetails implements UserDetails {

    private Users user;

    public CustomUserDetails(Users user) {
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Employees> employees = user.getEmployees();
        List<SimpleGrantedAuthority> authorities =new ArrayList<>();

        for(Employees em : employees){
            authorities.add(new SimpleGrantedAuthority(em.getEmployeeName()));

        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPass();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isStatus() ;
    }


}
