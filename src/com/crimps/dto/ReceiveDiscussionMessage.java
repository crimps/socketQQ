package com.crimps.dto;

/**
 * 讨论组消息类
 *
 * @author crimps
 * @create 2017-10-24 15:18
 **/
public class ReceiveDiscussionMessage extends ReceiveBaseMessage {
    public String fromDiscuss;

    public String getFromDiscuss() {
        return fromDiscuss;
    }

    public void setFromDiscuss(String fromDiscuss) {
        this.fromDiscuss = fromDiscuss;
    }
}
