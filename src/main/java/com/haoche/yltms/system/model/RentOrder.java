package com.haoche.yltms.system.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "rent_order", schema = "ylt")
public class RentOrder extends BaseModel {
    public static final String UN_PAY = "0"; //"未支付";
    public static final String PAY = "1"; //"已支付";
    public static final String OBTAIN_CAR = "2";//"已取车";
    public static final String RETURN_CAR = "3";//"已还车";
    public static final String CANCEL = "4";//"已取消";
    public static final String INVALID = "5";//"已作废";

    private User user;
    private Vehicle vehicle;
    private BigDecimal rent;
    private BigDecimal costRent;
    private String obtainProv;
    private String obtainCity;
    private String obtainArea;
    private String returnProv;
    private String returnCity;
    private String returnArea;
    private String orderStatus;
    private String orderNo;
    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date obtainTime;
    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date returnTime;
    private String obtainShop;
    private String returnShop;

    private String userId;
    private String vehicleId;
    private Date finishTime;
    private String duration;

    @Transient
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Transient
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "USER_ID", nullable = true)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "VEHICLE_ID", nullable = true)
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Basic
    @Column(name = "RENT")
    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    @Basic
    @Column(name = "COST_RENT")
    public BigDecimal getCostRent() {
        return costRent;
    }

    public void setCostRent(BigDecimal costRent) {
        this.costRent = costRent;
    }

    @Basic
    @Column(name = "OBTAIN_PROV")
    public String getObtainProv() {
        return obtainProv;
    }

    public void setObtainProv(String obtainProv) {
        this.obtainProv = obtainProv;
    }

    @Basic
    @Column(name = "OBTAIN_CITY")
    public String getObtainCity() {
        return obtainCity;
    }

    public void setObtainCity(String obtainCity) {
        this.obtainCity = obtainCity;
    }

    @Basic
    @Column(name = "OBTAIN_AREA")
    public String getObtainArea() {
        return obtainArea;
    }

    public void setObtainArea(String obtainArea) {
        this.obtainArea = obtainArea;
    }

    @Basic
    @Column(name = "RETURN_PROV")
    public String getReturnProv() {
        return returnProv;
    }

    public void setReturnProv(String returnProv) {
        this.returnProv = returnProv;
    }

    @Basic
    @Column(name = "RETURN_CITY")
    public String getReturnCity() {
        return returnCity;
    }

    public void setReturnCity(String returnCity) {
        this.returnCity = returnCity;
    }

    @Basic
    @Column(name = "RETURN_AREA")
    public String getReturnArea() {
        return returnArea;
    }

    public void setReturnArea(String returnArea) {
        this.returnArea = returnArea;
    }

    @Basic
    @Column(name = "ORDER_STATUS")
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Basic
    @Column(name = "ORDER_NO")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "OBTAIN_TIME")
    public Date getObtainTime() {
        return obtainTime;
    }

    public void setObtainTime(Date obtainTime) {
        this.obtainTime = obtainTime;
    }

    @Basic
    @Column(name = "RETURN_TIME")
    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    @Basic
    @Column(name = "OBTAIN_SHOP")
    public String getObtainShop() {
        return obtainShop;
    }

    public void setObtainShop(String obtainShop) {
        this.obtainShop = obtainShop;
    }

    @Basic
    @Column(name = "RETURN_SHOP")
    public String getReturnShop() {
        return returnShop;
    }

    public void setReturnShop(String returnShop) {
        this.returnShop = returnShop;
    }

    @Basic
    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Basic
    @Column(name = "DURATION")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
