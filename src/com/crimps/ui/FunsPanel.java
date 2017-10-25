package com.crimps.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * 功能面板
 *
 * @author crimps
 * @create 2017-10-24 15:56
 **/
public class FunsPanel extends BasePanel {

    private JTabbedPane tabbedPane;

    public FunsPanel(MainUI mainUI) {
        super(mainUI);
        this.setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        init();
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public void init() {
        Map<String, BasePanel> panelMap = mainUI.getMainUI().funPanelMap;
        for (String panelName : panelMap.keySet()) {
            tabbedPane.add(panelName, panelMap.get(panelName));
        }
    }
}
