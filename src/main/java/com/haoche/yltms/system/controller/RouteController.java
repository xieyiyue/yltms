package com.haoche.yltms.system.controller;

import com.haoche.yltms.config.LoginInterceptor;
import com.haoche.yltms.system.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/route")
public class RouteController {

    @RequestMapping("/browseVehicle")
    public String browseVehicle(HttpSession session, Model model){
        User user = (User) session.getAttribute(LoginInterceptor.SESSION_KEY);
        model.addAttribute("account",user);
        return "ylt/lease";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(LoginInterceptor.SESSION_KEY);
        return "redirect:/";
    }

}
