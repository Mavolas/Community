package com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean;

import java.util.Date;

/**
 * Created by 宋棋安
 * on 2018/6/20.
 */
public class FilterBean {

    private String bidingName;

    private String bidingCode;

    private String status;

    private String department;

    private Date dayFrom;

    private Date dayTo;


    public void setBidingName(String bidingName) {
        this.bidingName = bidingName;
    }

    public void setBidingCode(String bidingCode) {
        this.bidingCode = bidingCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDayFrom(Date dayFrom) {
        this.dayFrom = dayFrom;
    }

    public void setDayTo(Date dayTo) {
        this.dayTo = dayTo;
    }

    public String getBidingName() {
        return bidingName;
    }

    public String getBidingCode() {
        return bidingCode;
    }

    public String getStatus() {
        return status;
    }

    public String getDepartment() {
        return department;
    }

    public Date getDayFrom() {
        return dayFrom;
    }

    public Date getDayTo() {
        return dayTo;
    }
}
