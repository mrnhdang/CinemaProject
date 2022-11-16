package com.spring.cinemaproject.Controllers;

import com.spring.cinemaproject.Models.Memberships;
import com.spring.cinemaproject.Models.Users;
import com.spring.cinemaproject.Models.Utility;
import com.spring.cinemaproject.Repositories.MembershipRepository;
import com.spring.cinemaproject.Repositories.UserRepository;

import com.spring.cinemaproject.Repositories.VoucherRepository;
import com.spring.cinemaproject.Services.UserService;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @GetMapping("")
    public String viewHomePage(){
        return "Account/index";
    }
    @GetMapping("/login")
    public String login(Model model){
        return "Account/login";
    }

    @RequestMapping("/edit")
    public String edit(){
        return "Account/verify_success";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model){
        model.addAttribute("user",new Users());
        return "Account/register";
    }

    @PostMapping("/process_register")
    public String processRegistration(Users user, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws MessagingException, UnsupportedEncodingException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Date currentDate = Calendar.getInstance().getTime();

        Users existEmail = userRepository.findByEmail(user.getEmail());
        if(existEmail != null) {
            redirectAttributes.addFlashAttribute("message", "Email is already existed !!!!");
            return "redirect:/register";
        }
        String randomCode = RandomString.make(10);
        user.setVerificationCode(randomCode);

        String encodedPass = encoder.encode(user.getUserPass());
        user.setStatus(false);
        user.setUserPass(encodedPass);
        user.setCreateDate(currentDate);
        userRepository.save(user);

        Memberships memberships = new Memberships();
        memberships.setUsers(user);
        memberships.setPoints(10);
        membershipRepository.save(memberships);

        user.setMemberships(memberships);
        userRepository.save(user);

        String siteURL = Utility.getSiteURL(request);
        userService.sendVerificationEMail(user,siteURL);

        return "Account/register_success";
    }
    @GetMapping("/list_users")
    public String viewUserList(Model model){
        List<Users> list= userRepository.findAll();
        model.addAttribute("list_users", list);
        return "Account/users";
    }
    @RequestMapping("/profile")
    public String profile(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        Users user = userRepository.findByEmail(email);
        if(user == null){
            return "Account/login";
        }
        Memberships memberships = new Memberships();
        membershipRepository.findMembershipsByUsersId(user.getUserID());

        model.addAttribute("membership",membershipRepository.findMembershipsByUsersId(user.getUserID()));
        model.addAttribute("vouchers", voucherRepository.findVouchersForUser(user)) ;
        model.addAttribute("user",user);

        return "Account/profile";
    }
    @PostMapping("/update")
    public String editProfile(@RequestParam("username") String username, @RequestParam("address") String address,
                              @RequestParam("password") String password,@RequestParam("newPassword") String newPassword,
                              RedirectAttributes redirectAttributes, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        newPassword= bCryptPasswordEncoder.encode(newPassword);

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        Users user = userRepository.findByEmail(email);

        if(user != null){
            user.setUserName(username);
            user.setAddress(address);
            if(bCryptPasswordEncoder.matches(password,user.getUserPass())){
                user.setUserPass(newPassword);
                userRepository.save(user);
            }
            else{
                redirectAttributes.addFlashAttribute("changePassFailed", "Password is not match !!!!!!" );
            }
            redirectAttributes.addFlashAttribute("message", "Update successfull !!!!!!");
            model.addAttribute("user",user);
            return "redirect:/profile";
        }

        return "403";
    }
    @GetMapping("/403")
    public String error403(){
        return "403";
    }
    @GetMapping("/verify")
    public String verify(@Param("code") String code ,Model model){
        boolean verify = userService.verify(code);

        String pageTitle = verify ? "Vertification Succeeded!" : "Vertification Failed!";
        model.addAttribute("pageTitle", pageTitle);
        return "Account/"+ (verify ? "verify_success" : "verify_failed" );
    }
}

