package com.example.demo.dal.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Travelrecord {
    private Long id;

    private String userId;

    private Date traveldate;

    private BigDecimal fee;

    private Integer days;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getTraveldate() {
        return traveldate;
    }

    public void setTraveldate(Date traveldate) {
        this.traveldate = traveldate;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}