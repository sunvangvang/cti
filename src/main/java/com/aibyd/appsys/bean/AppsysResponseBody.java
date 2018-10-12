package com.aibyd.appsys.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 */

public class AppsysResponseBody implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;

    private String msg;

    private JSONObject result;

    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setJwtToken(String token) {
        this.token = token;
    }

}