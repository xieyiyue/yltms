package com.haoche.yltms.system.controller;

import com.haoche.yltms.system.model.Favorable;
import com.haoche.yltms.system.model.Vehicle;
import com.haoche.yltms.system.service.FavorableService;
import com.haoche.yltms.system.service.ShopService;
import com.haoche.yltms.system.service.VehicleService;
import com.haoche.yltms.system.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/unverify")
public class UnverifyController {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private FavorableService favorableService;

    @ResponseBody
    @RequestMapping("/getVehicleInfo")
    public Result getUserTable(String type){
        Result result = new Result();
        Map<String,String> params = new HashMap<>();
        params.put("type",type);
        try {
            List<Vehicle> vehicles = this.vehicleService.findVehicles(params);
            result.setSuccess(true);
            result.setObj(vehicles);
        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAddress")
    public Result getAddress(String type, String prov, String city, String area){
        Result result = new Result();
        Map<String,String> params = new HashMap<>();
        params.put("type",type);
        params.put("prov",prov);
        params.put("city",city);
        params.put("area",area);
        try {
            List<String> shops = this.shopService.findShops(params);
            result.setSuccess(true);
            result.setObj(shops);
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getFavorableInfo")
    public Result getFavorableInfo(){
        Result result = new Result();
        Map<String,String> params = new HashMap<>();
        try {
            List<Favorable> favorables = this.favorableService.findFavorables(params);
            result.setSuccess(true);
            result.setObj(favorables);
        } catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
