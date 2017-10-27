package com.crimps.ui;

import com.alibaba.fastjson.JSON;
import com.crimps.dto.Message;
import com.crimps.dto.SendGroupMessage;

import javax.swing.*;

/**
 * 基本面板功能
 *
 * @author crimps
 * @create 2017-10-24 13:53
 **/
public class BasePanel extends JPanel{
    protected MainUI mainUI;

    public MainUI getMainUI() {
        return mainUI;
    }

    public void setMainUI(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    public BasePanel(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    /**
     * 发送消息
     * @param message 消息内容
     */
    public void sendMessage(String message) {
        mainUI.getMainUI().sendMessage(message);
    }

    /**
     * 发送群消息
     * @param groupid 群ID
     * @param content 内容
     */
    public void sendGroupMessage(String groupid, String content) {
        SendGroupMessage sendGroupMessage = new SendGroupMessage();
        sendGroupMessage.setAct(Message.SEND_GROUP);
        sendGroupMessage.setGroupid(groupid);
        sendGroupMessage.setMsg(content);
        sendMessage(JSON.toJSONString(sendGroupMessage));
        System.out.println(JSON.toJSONString(sendGroupMessage));
    }

    /**
     * 处理消息
     * @param message 消息内容
     */
    public void data(String message) {

    }
}
