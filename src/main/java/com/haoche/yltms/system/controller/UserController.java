package com.haoche.yltms.system.controller;

import com.haoche.yltms.config.LoginInterceptor;
import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.service.LoginService;
import com.haoche.yltms.system.service.UserService;
import com.haoche.yltms.system.vo.Result;
import com.haoche.yltms.system.vo.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @RequestMapping("/saveUser")
    @ResponseBody
    public Result saveUser(User user) {
        Result result = new Result();
        try {
            if (this.loginService.getUserByUsername(user.getUsername(), User.GENERAL_USER) != null) {
                result.setSuccess(false);
                result.setMsg("该用户名已被使用");
                return result;
            }

            this.userService.save(user);
            result.setSuccess(true);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/update")
    public Result update(HttpServletRequest request, User user){
        Result result = new Result();
        try {
            if(StringUtils.isEmpty(user.getPasswordO())){
                HttpSession session = request.getSession();
                User account = (User) session.getAttribute(LoginInterceptor.SESSION_KEY);
                user.setModifier(account.getId());
                this.userService.save(user);
                result.setSuccess(true);
            }else{
                this.userService.changePassword(user);
                result.setSuccess(true);
            }
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/del")
    public Result del(@SessionAttribute(LoginInterceptor.SESSION_KEY)User user,String id){
        Result result = new Result();
        try {
            if(StringUtils.isEmpty(id)){
                result.setMsg("删除失败，无法确定id");
                result.setSuccess(false);
            }else{
                this.userService.del(id, user.getId());
                result.setSuccess(true);
            }
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/query")
    public String query(@SessionAttribute(LoginInterceptor.SESSION_KEY)User user, Model model){
        model.addAttribute("account",user);
        return "user/query";
    }

    @RequestMapping("/edit")
    public String edit(String id, Model model){
        User user = this.userService.findById(id);
        model.addAttribute("obj",user);
        return "user/edit";
    }

    @ResponseBody
    @RequestMapping("/getUserTable")
    public TableData getUserTable(Integer page, Integer limit, String username){
        TableData tableData = new TableData();
        Map<String,String> params = new HashMap<>();
        params.put("username",username);
        try {
            Page<User> userPage = this.userService.findUsers(page,limit,params);
            tableData.setCode(TableData.SUCCESS);
            tableData.setCount(userPage.getTotalElements());
            tableData.setData(userPage.getContent());
        } catch (Exception e){
            e.printStackTrace();
            tableData.setMsg(e.getMessage());
        }
        return tableData;
    }

    @ResponseBody
    @RequestMapping("/upgradeVip")
    public Result upgradeVip(String id){
        Result result = new Result();
        try {
            User user = this.userService.findById(id);
            user.setIsVip("1");
            user.setBalance(user.getBalance().subtract(new BigDecimal("899")));
            if(user.getBalance().compareTo(BigDecimal.ZERO) < 0){
                result.setSuccess(false);
                result.setMsg("余额不足");
                return result;
            }
            this.userService.save(user);
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/recharge")
    public Result recharge(String id, String money){
        Result result = new Result();
        try {
            User user = this.userService.findById(id);
            user.setBalance(user.getBalance().add(new BigDecimal(money)));
            this.userService.save(user);
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
