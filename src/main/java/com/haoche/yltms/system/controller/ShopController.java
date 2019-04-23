package com.haoche.yltms.system.controller;

import com.haoche.yltms.config.LoginInterceptor;
import com.haoche.yltms.system.model.Shop;
import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.service.ShopService;
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
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @RequestMapping("/query")
    public String query(@SessionAttribute(LoginInterceptor.SESSION_KEY) User user, Model model){
        model.addAttribute("account",user);
        return "shop/query";
    }

    @RequestMapping("/edit")
    public String edit(String id,Model model){
        if(!StringUtils.isEmpty(id)){
            Shop shop = this.shopService.findById(id);
            model.addAttribute("obj",shop);
        }else{
            model.addAttribute("obj",new Shop());
        }
        return "shop/edit";
    }

    @ResponseBody
    @RequestMapping("/getShopTable")
    public TableData getUserTable(Integer page, Integer limit, String shopName){
        TableData tableData = new TableData();
        Map<String,String> params = new HashMap<>();
        params.put("shopName",shopName);
        try {
            Page<Shop> shopPage = this.shopService.findShops(page,limit,params);
            tableData.setCode(TableData.SUCCESS);
            tableData.setCount(shopPage.getTotalElements());
            tableData.setData(shopPage.getContent());
        } catch (Exception e){
            e.printStackTrace();
            tableData.setMsg(e.getMessage());
        }
        return tableData;
    }

    @ResponseBody
    @RequestMapping("/save")
    public Result save(@SessionAttribute(LoginInterceptor.SESSION_KEY) User user, Shop shop){
        Result result = new Result();
        try {
            this.shopService.saveAndUpdate(shop,user);
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
    public Result del(@SessionAttribute(LoginInterceptor.SESSION_KEY)User user, String id){
        Result result = new Result();
        try {
            if(StringUtils.isEmpty(id)){
                result.setMsg("删除失败，无法确定id");
                result.setSuccess(false);
            }else{
                this.shopService.del(id, user.getId());
                result.setSuccess(true);
            }
        } catch (Exception e){
            e.printStackTrace();
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
