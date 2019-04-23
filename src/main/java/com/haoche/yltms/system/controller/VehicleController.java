package com.haoche.yltms.system.controller;

import com.haoche.yltms.config.LoginInterceptor;
import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.model.Vehicle;
import com.haoche.yltms.system.service.VehicleService;
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
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @RequestMapping("/query")
    public String query(@SessionAttribute(LoginInterceptor.SESSION_KEY)User user, Model model){
        model.addAttribute("account",user);
        return "vehicle/query";
    }

    @RequestMapping("/edit")
    public String edit(String id,Model model){
        if(!StringUtils.isEmpty(id)){
            Vehicle vehicle = this.vehicleService.findById(id);
            model.addAttribute("obj",vehicle);
        }else{
            model.addAttribute("obj",new Vehicle());
        }
        return "vehicle/edit";
    }

    @ResponseBody
    @RequestMapping("/getVehicleTable")
    public TableData getUserTable(Integer page, Integer limit, String license){
        TableData tableData = new TableData();
        Map<String,String> params = new HashMap<>();
        params.put("license",license);
        try {
            Page<Vehicle> vehiclePage = this.vehicleService.findVehicles(page,limit,params);
            tableData.setCode(TableData.SUCCESS);
            tableData.setCount(vehiclePage.getTotalElements());
            tableData.setData(vehiclePage.getContent());
        } catch (Exception e){
            e.printStackTrace();
            tableData.setMsg(e.getMessage());
        }
        return tableData;
    }

    @ResponseBody
    @RequestMapping("/save")
    public Result save(@SessionAttribute(LoginInterceptor.SESSION_KEY) User user, Vehicle vehicle){
        Result result = new Result();
        try {
            this.vehicleService.saveAndUpdate(vehicle,user);
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
                this.vehicleService.del(id, user.getId());
                result.setSuccess(true);
            }
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
