package com.crimps.dto;

/**
 * 发送消息类
 *
 * @author crimps
 * @create 2017-10-24 15:35
 **/
public class SendMessage {
    public String act;
    public String QQID;
    public String msg;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getQQID() {
        return QQID;
    }

    public void setQQID(String QQID) {
        this.QQID = QQID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
