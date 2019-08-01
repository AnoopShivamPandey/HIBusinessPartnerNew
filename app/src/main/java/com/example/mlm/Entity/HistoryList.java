package com.example.mlm.Entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryList implements Serializable {

    String id,
            account_id,
            user_id,
            source,
            from_funding,
            amount,
            level,
            paired_from,
            left_user_id,
            right_user_id,
            purchase_code_id,
            created_at,
            updated_at,
            message;

    List<HistoryList> paginate=new ArrayList<>();

    public List<HistoryList> getPaginate() {
        return paginate;
    }

    public void setPaginate(List<HistoryList> paginate) {
        this.paginate = paginate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFrom_funding() {
        return from_funding;
    }

    public void setFrom_funding(String from_funding) {
        this.from_funding = from_funding;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPaired_from() {
        return paired_from;
    }

    public void setPaired_from(String paired_from) {
        this.paired_from = paired_from;
    }

    public String getLeft_user_id() {
        return left_user_id;
    }

    public void setLeft_user_id(String left_user_id) {
        this.left_user_id = left_user_id;
    }

    public String getRight_user_id() {
        return right_user_id;
    }

    public void setRight_user_id(String right_user_id) {
        this.right_user_id = right_user_id;
    }

    public String getPurchase_code_id() {
        return purchase_code_id;
    }

    public void setPurchase_code_id(String purchase_code_id) {
        this.purchase_code_id = purchase_code_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static List<HistoryList> createListFromObject(String json) {
        Gson gson = new Gson();
        Type type=new TypeToken<List<HistoryList>>(){}.getType();
        return gson.fromJson(json,type);
    }

}

