package com.crimps.dto;

/**
 * 发送群消息类
 *
 * @author crimps
 * @create 2017-10-27 10:43
 **/
public class SendGroupMessage {
    private String act;
    private String groupid;
    private String msg;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
