package com.crimps.service.socket;

import com.crimps.ui.MainUI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * QQ scoket客户端
 *
 * @author crimps
 * @create 2017-10-24 9:04
 **/
public class QQClinet {
    public static WebSocketClient webSocketClient;
    public static boolean connected = false;
    private String lemocUrl = "ws://localhost:25303";
    private MainUI mainUI;

    public QQClinet(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    /**
     * qq socket 连接
     * @return true:false
     */
    public boolean connect(){
        try {
            webSocketClient = new WebSocketClient(new URI(lemocUrl), (Draft) new Draft_17()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    connected = true;
                    System.out.println("onOpen");
                }

                @Override
                public void onMessage(String s) {
                    mainUI.getMainUI().onMessage(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    connected = false;
                    System.out.println("onClose");
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("onError");
                    connected = false;
                    e.printStackTrace();
                }
            };
            webSocketClient.connect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送消息
     * @param message 消息内容
     */
    public void sendMessage(String message) {
        webSocketClient.send(message);
    }
}
