package com.crimps.ui;

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
    protected void sendMessage(String message) {
        mainUI.getMainUI().sendMessage(message);
    }

    /**
     * 处理消息
     * @param message 消息内容
     */
    public void data(String message) {

    }
}
