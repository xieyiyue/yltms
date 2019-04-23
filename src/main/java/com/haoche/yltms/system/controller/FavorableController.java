package com.haoche.yltms.system.controller;

import com.haoche.yltms.config.LoginInterceptor;
import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.model.Favorable;
import com.haoche.yltms.system.service.FavorableService;
import com.haoche.yltms.system.vo.Result;
import com.haoche.yltms.system.vo.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/favorable")
public class FavorableController {
    @Autowired
    private FavorableService favorableService;
    @RequestMapping("/query")
    public String query(@SessionAttribute(LoginInterceptor.SESSION_KEY) User user, Model model){
        model.addAttribute("account",user);
        return "favorable/query";
    }

    @RequestMapping("/edit")
    public String edit(String id,Model model){
        if(!StringUtils.isEmpty(id)){
            Favorable favorable = this.favorableService.findById(id);
            model.addAttribute("obj",favorable);
        }else{
            model.addAttribute("obj",new Favorable());
        }
        return "favorable/edit";
    }

    @ResponseBody
    @RequestMapping("/getFavorableTable")
    public TableData getUserTable(Integer page, Integer limit, String model){
        TableData tableData = new TableData();
        Map<String,String> params = new HashMap<>();
        params.put("model",model);
        try {
            Page<Favorable> favorablePage = this.favorableService.findFavorables(page,limit,params);
            tableData.setCode(TableData.SUCCESS);
            tableData.setCount(favorablePage.getTotalElements());
            tableData.setData(favorablePage.getContent());
        } catch (Exception e){
            e.printStackTrace();
            tableData.setMsg(e.getMessage());
        }
        return tableData;
    }

    @ResponseBody
    @RequestMapping("/save")
    public Result save(@SessionAttribute(LoginInterceptor.SESSION_KEY) User user, Favorable favorable){
        Result result = new Result();
        try {
            this.favorableService.saveAndUpdate(favorable,user);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
            result.setSuccess(false);
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
                this.favorableService.del(id, user.getId());
                result.setSuccess(true);
            }
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
