package com.haoche.yltms.system.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "vehicle", schema = "ylt", catalog = "")
public class Vehicle extends BaseModel {
    public static final String ENABLE = "0";
    public static final String DISABLE = "1";

    public static final String NORMAL = "1";
    public static final String LUXURY = "2";

    private String license;
    private String brand;
    private String model;
    private String seatNum;
    private String colour;
    private String vin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date firstLicense;
    private String isDelete;
    private BigDecimal rent;
    private String isStop;
    private String imgPath;
    private String type;
    private String cubicles;
    private String exhaust;

    @Basic
    @Column(name = "IMG_PATH")
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Basic
    @Column(name = "LICENSE")
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Basic
    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "MODEL")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "SEAT_NUM")
    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    @Basic
    @Column(name = "COLOUR")
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Basic
    @Column(name = "VIN")
    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Basic
    @Column(name = "FIRST_LICENSE")
    public Date getFirstLicense() {
        return firstLicense;
    }

    public void setFirstLicense(Date firstLicense) {
        this.firstLicense = firstLicense;
    }

    @Basic
    @Column(name = "IS_DELETE")
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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
    @Column(name = "IS_STOP")
    public String getIsStop() {
        return isStop;
    }

    public void setIsStop(String isStop) {
        this.isStop = isStop;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "CUBICLES")
    public String getCubicles() {
        return cubicles;
    }

    public void setCubicles(String cubicles) {
        this.cubicles = cubicles;
    }

    @Basic
    @Column(name = "EXHAUST")
    public String getExhaust() {
        return exhaust;
    }

    public void setExhaust(String exhaust) {
        this.exhaust = exhaust;
    }

}
