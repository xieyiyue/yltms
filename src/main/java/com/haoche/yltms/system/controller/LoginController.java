package com.haoche.yltms.system.controller;

import com.haoche.yltms.config.LoginInterceptor;
import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.service.LoginService;
import com.haoche.yltms.system.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;


    @RequestMapping("/")
    public String login(HttpSession session, Model model) {
        User user = (User) session.getAttribute(LoginInterceptor.SESSION_KEY);
        model.addAttribute("account",user);
        return "ylt/index";
    }

    @RequestMapping("/signUp")
    public String signUp() {
        return "signUp";
    }

    @RequestMapping("/admLogin")
    public String admLogin() {
        return "admLogin";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result content(HttpSession session, String username, String password,String type) {
        Result result = new Result();
        User user = this.loginService.getUserByUsername(username, type);
        if(user != null && password.equals(user.getPassword())){
            session.setAttribute(LoginInterceptor.SESSION_KEY, user);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setMsg("用户名或密码错误");
        }
        return result;
    }

    @RequestMapping("/index")
    public String index(@SessionAttribute(LoginInterceptor.SESSION_KEY)User user, Model model) {
        model.addAttribute("account",user);
        return "admIndex";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(LoginInterceptor.SESSION_KEY);
        return "redirect:/admLogin";
    }

    @RequestMapping("/editPass")
    public String editPass(@SessionAttribute(LoginInterceptor.SESSION_KEY)User user, Model model){
        model.addAttribute("account",user);
        return "passForm";
    }


}
