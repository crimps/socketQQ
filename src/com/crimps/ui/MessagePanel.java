package com.crimps.ui;

import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 原始消息面板
 *
 * @author crimps
 * @create 2017-10-24 14:01
 **/
public class MessagePanel extends BasePanel {
    private JTextArea textArea;
    private JButton loginButton;

    public MessagePanel(MainUI mainUI) {
        super(mainUI);
        this.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setForeground(Color.BLUE);
        JScrollPane messageScrollPane = new JScrollPane(textArea);
        messageScrollPane.setBorder(new TitledBorder("原始消息"));
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(messageScrollPane, BorderLayout.CENTER);
        initLoginButton();
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initLoginButton() {
        loginButton = new JButton();
        loginButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainUI.getMainUI().connect();
            }
        });
        loginButton.setText("登录");
    }

    public void data(String message) {
        if (StringUtils.isNotBlank(message)) {
            textArea.append(message);
            textArea.setCaretPosition(textArea.getText().length());
        }
    }
}
