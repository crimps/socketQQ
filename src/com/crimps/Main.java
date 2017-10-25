package com.crimps;

import com.crimps.ui.MainUI;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
