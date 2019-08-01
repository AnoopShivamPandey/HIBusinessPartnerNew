package com.example.mlm.Entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class RightNodeList implements Serializable {
    String id,
            member_id,
            first_name,
            last_name,
            mobile,
            payment_status,
            payment_date,
            photo,
            joining_date,
            joining_date_format;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getJoining_date() {
        return joining_date;
    }

    public void setJoining_date(String joining_date) {
        this.joining_date = joining_date;
    }

    public String getJoining_date_format() {
        return joining_date_format;
    }

    public void setJoining_date_format(String joining_date_format) {
        this.joining_date_format = joining_date_format;
    }


    public static List<RightNodeList> createListFromObject(String json) {
        Gson gson = new Gson();
        Type type=new TypeToken<List<RightNodeList>>(){}.getType();
        return gson.fromJson(json,type);
    }

}