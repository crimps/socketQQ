package com.crimps.service.lifelineone;

import com.alibaba.fastjson.JSON;
import com.crimps.ui.LifeLinePanel;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by crimps on 2017/6/14.
 */
public class LifeLine {
    private static StringBuffer gameBlock = new StringBuffer("");
    private LifeLinePanel lifeLinePanel;

    public LifeLine(LifeLinePanel lifeLinePanel) {
        this.lifeLinePanel = lifeLinePanel;
    }

    /**
     * 开始游戏
     */
    public void startGame(String qqId) {
        process("launch", qqId);
    }

    /**
     * lifeline游戏进度处理
     * @param content
     * @return
     */
    public void process(String content, String qqId) {
        if ("gameover".equalsIgnoreCase(content)) {
            System.out.println("####################################################################################################");
        } else {
            //读取游戏进度
            String progressBlock;
            String progressTime;
            if (StringUtils.isNotBlank(content)) {
                progressBlock = content;
            } else {
                Map<String, String> progressMap = ReadStoryData.readGameProgress();
                progressBlock = progressMap.get(ReadStoryData.PROCESS_BLOCK);
                progressTime = progressMap.get(ReadStoryData.PROCESS_TIME);
            }
            //当前内容与游戏进度是否匹配

            //游戏进度处理
            //若游戏内容为空，读取游戏内容
            if (null == ReadStoryData.choicesMap || null == ReadStoryData.scenesMap) {
                ReadStoryData.readStoryDate();
            }
            //根据block，获取场景内容
            List<String> scenesList = ReadStoryData.scenesMap.get(progressBlock);
            Map<String, Object> resultMap = ScenesProcess.processScenes(scenesList);
            //跳转
            if (ScenesProcess.RESULT_SCENES.equals(resultMap.get(ScenesProcess.RESULT_TYPE_KEY).toString())) {
                for (String str : (List<String>) resultMap.get(ScenesProcess.RESULT_CONTENT_KEY)) {
                    gameBlock.append(str);
                    System.out.println("$$$$$$$$$$$$$" + str);
                    sendMessage(str, qqId);
                }
                process(resultMap.get(ScenesProcess.RESULT_TYPE_VALUE).toString(), qqId);
            }
            //选择
            if (ScenesProcess.RESULT_CHOIC.equals(resultMap.get(ScenesProcess.RESULT_TYPE_KEY).toString())) {
                Map<String, String> progressMap = ReadStoryData.readGameProgress();
                for (String str : (List<String>) resultMap.get(ScenesProcess.RESULT_CONTENT_KEY)) {
                    System.out.println("$$$$$$$$$$$$$" + str);
                    gameBlock.append(str);
                    sendMessage(str, qqId);
                }
                String choiceKey = resultMap.get(ScenesProcess.RESULT_TYPE_VALUE).toString();
                Map<String, String> choiceMap = ReadStoryData.choicesMap.get(StringUtils.trim(choiceKey));
                int index = 0;
                StringBuffer tempBuffer = new StringBuffer("");
                for (String key : choiceMap.keySet()) {
                    System.out.println("#" + index + choiceMap.get(key));
                    gameBlock.append("#" + index + choiceMap.get(key));
                    progressMap.put("#" + index, choiceMap.get(key));
                    tempBuffer.append("(#").append(index).append(")").append(choiceMap.get(key));
                    if (0 == index) {
                        tempBuffer.append("\n");
                    }
                    index++;
                }
                sendMessage(tempBuffer.toString(), qqId);
                //记录游戏进度
                progressMap.put(ReadStoryData.PROCESS_CHOICE, StringUtils.trim(choiceKey));
                progressMap.put(ReadStoryData.PROCESS_BLOCK, gameBlock.toString());
                String temp = JSON.toJSONString(progressMap);
                ReadStoryData.writeGameProgress(JSON.toJSONString(progressMap));
                System.out.println(temp);
            }
        }
    }

    /**
     * 选择模块
     * @param choice 选择语句
     */
    public void choice(String choice, String qqId) {
        gameBlock.setLength(0);
        Map<String, String> gameProgressMap = ReadStoryData.readGameProgress();
        String choiceCn = gameProgressMap.get(choice).toString();
        String keyChoice = gameProgressMap.get(ReadStoryData.PROCESS_CHOICE);
        if (null == ReadStoryData.choicesMap || null == ReadStoryData.scenesMap) {
            ReadStoryData.readStoryDate();
        }
        Map<String, String> choiceMap = ReadStoryData.choicesMap.get(keyChoice);
        for (String scense : choiceMap.keySet()) {
            if (choiceMap.get(scense).toString().equals(choiceCn)) {
                process(scense, qqId);
                break;
            }
        }
    }

    /**
     * 发送消息
     * @param content
     * @param qqId
     */
    private void sendMessage(String content, String qqId) {
        try {
            Random random = new Random();
            int time = 1000 + random.nextInt(3000);
            Thread.sleep(time);
            lifeLinePanel.sendGroupMessage(qqId, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
