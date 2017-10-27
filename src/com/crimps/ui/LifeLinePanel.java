package com.crimps.ui;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.crimps.dto.Message;
import com.crimps.dto.ReceiveGroupMessage;
import com.crimps.service.lifelineone.LifeLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 生命线系列
 *
 * @author crimps
 * @create 2017-10-27 9:07
 **/
public class LifeLinePanel extends BasePanel {

    private LifeLine lifeLine;

    private LifeLinePanel getLifeLinePanel() {
        return this;
    }

    public LifeLinePanel(MainUI mainUI) {
        super(mainUI);
        this.setLayout(new BorderLayout());
        JTextField qqIdTextField = new JTextField("621514353");
        JButton startGameButton = new JButton("开始");
        startGameButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lifeLine = new LifeLine(getLifeLinePanel());
                lifeLine.startGame("621514353");
                this.setEnabled(false);
            }
        });
        JPanel panel = new JPanel();
        panel.add(qqIdTextField);
        panel.add(startGameButton);
        this.add(panel, BorderLayout.CENTER);
    }

    public void data(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        String subType = jsonObject.getString(Message.MESSAGE_SUBTYPE);
        String qqId = jsonObject.getString("fromGroup");
        if (Message.REC_SUBTYPE_GROUP.equals(subType)) {
            ReceiveGroupMessage receiveGroupMessage = JSONObject.parseObject(message, new TypeReference<ReceiveGroupMessage>(){});
            String msg = receiveGroupMessage.getMsg();
            if ("#0".equalsIgnoreCase(msg) || "#1".equalsIgnoreCase(msg)) {
                lifeLine.choice(msg, qqId);
            }
        }
    }

}
