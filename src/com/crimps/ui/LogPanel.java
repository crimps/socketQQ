package com.crimps.ui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.crimps.dto.Message;
import com.crimps.dto.ReceiveBaseMessage;
import com.crimps.dto.ReceiveDiscussionMessage;
import com.crimps.dto.ReceiveGroupMessage;
import com.crimps.service.db.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.UUID;

/**
 * 消息记录面板
 *
 * @author crimps
 * @create 2017-10-24 14:42
 **/
public class LogPanel extends BasePanel {

    private final String dbName = "log.db";
    private final String MESSAGE_TABLE = "tb_base_message";
    private final String MESSAGE_GROUP_TABLE = "tb_group_message";
    private final String MESSAGE_DISCUSSION_TABLE = "tb_discussion_message";
    private DbUtils dbUtils;

    public LogPanel(MainUI mainUI) {
        super(mainUI);
        initDb();
        this.setLayout(new BorderLayout());
        JButton button = new JButton();
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void data(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        String subType = jsonObject.getString(Message.MESSAGE_SUBTYPE);
        try {
            if (Message.REC_SUBTYPE_GROUP.equals(subType)) {
                ReceiveGroupMessage receiveGroupMessage = JSONObject.parseObject(message,
                        new TypeReference<ReceiveGroupMessage>(){});
                receiveGroupMessage.setId(UUID.randomUUID().toString());
                System.out.println(dbUtils.save(MESSAGE_GROUP_TABLE, receiveGroupMessage));
            } else if (Message.REC_SUBTYPE_DISCUSSION.equals(subType)) {
                ReceiveDiscussionMessage receiveDiscussionMessage = JSONObject.parseObject(message,
                        new TypeReference<ReceiveDiscussionMessage>(){});
                receiveDiscussionMessage.setId(UUID.randomUUID().toString());
                dbUtils.save(MESSAGE_DISCUSSION_TABLE, receiveDiscussionMessage);
            } else if (Message.REC_SUBTYPE_DIRECT_FRIEND.equals(subType)) {
                ReceiveBaseMessage receiveBaseMessage = JSONObject.parseObject(message,
                        new TypeReference<ReceiveBaseMessage>(){});
                receiveBaseMessage.setId(UUID.randomUUID().toString());
                System.out.println(dbUtils.save(MESSAGE_TABLE, receiveBaseMessage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据库
     */
    private void initDb() {
        try {
            dbUtils = new DbUtils(dbName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
