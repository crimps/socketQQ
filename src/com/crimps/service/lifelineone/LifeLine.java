package com.crimps.service.lifelineone;

import java.util.List;
import java.util.Map;

/**
 * Created by crimps on 2017/6/14.
 */
public class LifeLine {
    /**
     * lifeline游戏进度处理
     * @param content
     * @return
     */
    public String process(String content) {
        //读取游戏进度
        Map<String, String> progressMap = ReadStoryData.readGameProgress();
        String progressBlock = progressMap.get(ReadStoryData.PROCESS_BLOCK);
        String progressTime = progressMap.get(ReadStoryData.PROCESS_TIME);
        //当前内容与游戏进度是否匹配

        //游戏进度处理
        //若游戏内容为空，读取游戏内容
        if (null == ReadStoryData.choicesMap || null == ReadStoryData.scenesMap) {
            ReadStoryData.readStoryDate();
        }
        //根据block，获取场景内容
        List<String> scenesList = ReadStoryData.scenesMap.get(progressBlock);
        StringBuffer sb = new StringBuffer("");
        for (String scene : scenesList) {
            sb.append(scene).append("\\r\\n");
        }
        //记录游戏进度

        //返回内容
        return sb.toString();
    }
}
