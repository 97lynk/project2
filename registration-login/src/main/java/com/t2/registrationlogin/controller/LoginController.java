/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.logging.Logger;

/**
 *
 * @author 97lynk
 */
@Controller
public class LoginController {

    private static final Logger logger
            = Logger.getLogger(LoginController.class.getName());

    // gửi form login
    @RequestMapping(value = "/u/login", method = RequestMethod.GET)
    public String loginPage(Authentication auth) {
//        User loginedUser = (User) ((Authentication) principal).getPrincipal();
//
//        String userInfo = loginedUser.toString();
        logger.info(SecurityContextHolder.getContext().toString());
        return "signin/loginPage";
    }

    // chuyển trang login thành công
    @RequestMapping(value = "/u/logout", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model, HttpServletRequest request,
                                       HttpServletResponse response) {
        model.addAttribute("title", "Logout");
        logger.info(SecurityContextHolder.getContext().getAuthentication()
                .toString());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        logger.info("logout success");
        return "signin/logoutSuccessfulPage";
    }

//    @RequestMapping(value = "/u/logout2", method = RequestMethod.GET)
//    public String logoutSuccessfulPage2(Model model) {
//        model.addAttribute("title", "Logout");
//        return "signin/logoutSuccessfulPage";
//    }

    // trang cho admin
    @RequestMapping(value = "/u/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
        //principal đối tượng cần xác thược
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = loginedUser.toString();
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", userInfo);

        return "adminPage";
    }

    @RequestMapping(value = "/u/info", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // (1) (en)
        // After user login successfully.
        // (vi)
        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = loginedUser.toString();
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
    }

    @RequestMapping(value = "/u/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = loginedUser.toString();

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }

}
