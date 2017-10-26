package com.crimps.ui;

import com.crimps.service.lifelineone.LifeLine;
import com.crimps.service.socket.QQClinet;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 主界面
 *
 * @author crimps
 * @create 2017-10-24 9:10
 **/
public class MainUI extends JFrame{

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    public static String rootPath;
    private QQClinet qqClinet;
    public Map<String, BasePanel> funPanelMap = new HashMap<>();
    /**
     * 原始消息面板
     */
    private MessagePanel messagePanel;

    /**
     * 功能面板
     */
    private FunsPanel funsPanel;

    public MainUI getMainUI() {
        return this;
    }

    public MainUI() {
        initPath();
        this.setSize(new Dimension(WIDTH, HEIGHT));
        messagePanel = new MessagePanel(getMainUI());
        initFuns();
        funsPanel = new FunsPanel(getMainUI());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, messagePanel, funsPanel);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);
        this.getContentPane().add(splitPane);
        this.setVisible(true);
    }

    /**
     * 初始化本地路径
     */
    private void initPath() {
        String path = MainUI.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (path.endsWith(".jar")) {
            rootPath = new File(path).getParentFile().getAbsolutePath();
        } else {
            rootPath = path;
        }
    }

    private void initFuns() {
        LogPanel logPanel = new LogPanel(getMainUI());
        funPanelMap.put("log", logPanel);
        LifeLine lifeLine = new LifeLine();
//        lifeLine.process("launch");
        lifeLine.choice("“准备得最不充分”？");
    }

    /**
     * 连接 qq socket服务
     * @return treu:false
     */
    public boolean connect() {
        if (null == qqClinet) {
            qqClinet = new QQClinet(getMainUI());
        }
        return qqClinet.connect();
    }

    /**
     * 接收消息
     * @param message 消息内容
     */
    public void onMessage(final String message) {
        Thread issueMessage = new Thread(){
            public void run() {
                System.out.println(message);
                messagePanel.data(message);
                for (String panelName : funPanelMap.keySet()) {
                    funPanelMap.get(panelName).data(message);
                }
            }
        };
        issueMessage.start();
    }

    /**
     * 发送消息
     * @param message 消息内容
     */
    public void sendMessage(String message) {
        qqClinet.sendMessage(message);
    }
}
