package com.example.arun.ampereplusapp.bean;

public class ApplianceBean {
    private String id, user_id, device_id, appliance_name;
    private CharSequence device_no;

    public CharSequence getDevice_no() {
        return device_no;
    }

    public void setDevice_no(CharSequence device_no) {
        this.device_no = device_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getAppliance_name() {
        return appliance_name;
    }

    public void setAppliance_name(String appliance_name) {
        this.appliance_name = appliance_name;
    }
}
