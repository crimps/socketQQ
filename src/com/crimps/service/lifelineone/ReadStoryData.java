package com.crimps.service.lifelineone;


import com.crimps.Main;
import com.crimps.ui.MainUI;
import com.crimps.utils.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parse;

/**
 * Created by crimps on 2017/6/14.
 */
public class ReadStoryData {
    private static final String FILE_DIR = MainUI.rootPath + "resources/lifelineonedata";
    private static final String FILE_SCENES = FILE_DIR + File.separator + "scenes_cn.json";
    private static final String FILE_CHOICES = FILE_DIR + File.separator + "choices_cn.json";
    private static final String FILE_PROCESS = FILE_DIR + File.separator + "GameProgress.json";
    private static final String FILE_PARAMETER = FILE_DIR + File.separator + "GameParameters.json";

    public static final String PROCESS_BLOCK = "gameBlock"; //游戏当前进度区块
    public static final String PROCESS_TIME = "time"; //游戏当前进度到达区块时间
    public static final String PROCESS_CHOICE = "choice"; //游戏当前进度选择区块
    private static final String CHOICE_ID = "identifier";
    private static final String CHOICE_ACTION = "actions";
    private static final String CHOICE_CONTENT = "choice";

    public static Map<String, Map<String, String>> choicesMap;
    public static Map<String, List<String>> scenesMap;

    /**
     * 读取lifeline游戏内容
     * @return
     */
    public static boolean readStoryDate() {
        try {
            StringBuffer scenesBuff = new StringBuffer();
            StringBuffer choicesBuff = new StringBuffer();
            FileUtil.readToBuffer(scenesBuff, FILE_SCENES);
            FileUtil.readToBuffer(choicesBuff, FILE_CHOICES);
            scenesMap = (Map<String, List<String>>)parse(scenesBuff.toString());
            choicesMap = new HashMap<String, Map<String, String>>();
            List<Object> choicesList = (List<Object>)parse(choicesBuff.toString());
            for (Object choice : choicesList) {
                Map<String, Object> map = (Map<String, Object>)choice;
                String id = (String) map.get(CHOICE_ID);
                List<Map<String, String>> kMap = (List<Map<String,String>>)map.get(CHOICE_ACTION);
                Map<String, String> choiceMap = new HashMap<String, String>();
                for (Map<String, String> tempMap : kMap) {
                    choiceMap.put(tempMap.get(CHOICE_ID), tempMap.get(CHOICE_CONTENT));
                }
                choicesMap.put(id, choiceMap);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取lifeline游戏进度
     * @return
     */
    public static Map<String, String> readGameProgress() {
        Map<String, String> gameProgressMap = new HashMap<String, String>();
        try {
            gameProgressMap = FileUtil.readToMap(FILE_PROCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameProgressMap;
    }

    /**
     * 写入lifeline游戏进度
     * @param content 文本内容
     * @return
     */
    public static boolean writeGameProgress(String content) {
        return FileUtil.writeToFile(content, FILE_PROCESS);
    }

    /**
     * 读取游戏参数
     * @return
     */
    public static Map<String, String> readGameParameter() {
        Map<String, String> gameParameterMap = new HashMap<String, String>();
        try {
            gameParameterMap = FileUtil.readToMap(FILE_PARAMETER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameParameterMap;
    }

    /**
     * 写入lifeline游戏参数
     * @param content 文本内容
     * @return
     */
    public static boolean writeGameParameter(String content) {
        return FileUtil.writeToFile(content, FILE_PARAMETER);
    }
}