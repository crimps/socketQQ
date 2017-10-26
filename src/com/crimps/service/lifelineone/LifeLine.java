package com.crimps.service.lifelineone;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by crimps on 2017/6/14.
 */
public class LifeLine {
    private static StringBuffer gameBlock = new StringBuffer("");

    /**
     * lifeline游戏进度处理
     * @param content
     * @return
     */
    public void process(String content) {
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
            }
            process(resultMap.get(ScenesProcess.RESULT_TYPE_VALUE).toString());
        }
        //选择
        if (ScenesProcess.RESULT_CHOIC.equals(resultMap.get(ScenesProcess.RESULT_TYPE_KEY).toString())) {
            for (String str : (List<String>) resultMap.get(ScenesProcess.RESULT_CONTENT_KEY)) {
                System.out.println("$$$$$$$$$$$$$" + str);
                gameBlock.append(str);
            }
            String choiceKey = resultMap.get(ScenesProcess.RESULT_TYPE_VALUE).toString();
            Map<String, String> choiceMap = ReadStoryData.choicesMap.get(StringUtils.trim(choiceKey));
            for (String key : choiceMap.keySet()) {
                System.out.println("CC" + choiceMap.get(key));
                gameBlock.append(choiceMap.get(key));
            }
            //记录游戏进度
            Map<String, String> progressMap = ReadStoryData.readGameProgress();
            progressMap.put(ReadStoryData.PROCESS_CHOICE, StringUtils.trim(choiceKey));
            progressMap.put(ReadStoryData.PROCESS_BLOCK, gameBlock.toString());
            String temp = JSON.toJSONString(progressMap);
            ReadStoryData.writeGameProgress(JSON.toJSONString(progressMap));
            System.out.println(temp);
        }
    }

    /**
     * 选择模块
     * @param choice 选择语句
     */
    public void choice(String choice) {
        gameBlock.setLength(0);
        String keyChoice = ReadStoryData.readGameProgress().get(ReadStoryData.PROCESS_CHOICE);
        if (null == ReadStoryData.choicesMap || null == ReadStoryData.scenesMap) {
            ReadStoryData.readStoryDate();
        }
        Map<String, String> choiceMap = ReadStoryData.choicesMap.get(keyChoice);
        for (String scense : choiceMap.keySet()) {
            if (choiceMap.get(scense).toString().equals(choice)) {
                process(scense);
                break;
            }
        }
    }
}
