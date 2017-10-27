package com.crimps.service.lifelineone;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crimps on 2017/6/16.
 */
public class ScenesProcess {
    private static final String IF = "<<if";
    private static final String ELSE = "<<else";
    private static final String ENDIF = "<<endif";
    public static final String RESULT_TYPE_KEY = "TYPE";
    public static final String RESULT_TYPE_VALUE = "VALUE";
    public static final String RESULT_CONTENT_KEY = "CONTENTS";
    public static final String RESULT_SCENES = "SCENES";
    public static final String RESULT_CHOIC = "CHOIC";

    /**
     * 场景逻辑处理
     *
     * @param scenesList 场景内容列表
     * @return
     */
    public static Map<String, Object> processScenes(List<String> scenesList) {
        StringBuffer sb = new StringBuffer("");
        //场景逻辑处理
        return logicProcess(scenesList);
    }

    /**
     * 逻辑处理模块
     *
     * @param scenesList
     */
    private static Map<String, Object> logicProcess(List<String> scenesList) {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> contentList = new ArrayList<>();
        boolean ifelse = false;
        boolean skipline = false;
        for (String scenes : scenesList) {
            //处理 if else endif
            if (ifelse) {
                if (scenes.contains(ELSE)) {
                    skipline = !skipline;
                    continue;
                } else if (scenes.contains(ENDIF)) {
                    ifelse = false;
                    continue;
                }
                if (skipline) {
                    continue;
                }
            }

            if (scenes.contains(IF)) {
                ifelse = true;
                skipline = !execIf(scenes);
            } else if (scenes.contains("<<set")) {
                //设置参数
                setParam(scenes);
                continue;
            } else if (scenes.contains("[[")) {
                //跳转场景,不作延时
                resultMap.put(RESULT_TYPE_KEY, RESULT_SCENES);
                resultMap.put(RESULT_CONTENT_KEY, contentList);
                if (scenes.contains("[[delay")) {
                    resultMap.put(RESULT_TYPE_VALUE, scenes.split("\\|")[1].replace("]]", ""));
                } else {
                    resultMap.put(RESULT_TYPE_VALUE, scenes.replace("[[", "").replace("]]", ""));
                }
                return resultMap;
            } else if (scenes.contains("<<category")) {
                //进入选择
                resultMap.put(RESULT_TYPE_KEY, RESULT_CHOIC);
                resultMap.put(RESULT_TYPE_VALUE, scenes.replace("<<category", "").replace(">>", ""));
                resultMap.put(RESULT_CONTENT_KEY, contentList);
                return resultMap;
            } else {
                contentList.add(scenes);
            }
        }
        resultMap.put(RESULT_TYPE_KEY, null);
        resultMap.put(RESULT_CONTENT_KEY, contentList);
        return resultMap;
    }

    /**
     * 判断if条件
     *
     * @param scenes if条件语句
     * @return true:flase
     */
    private static boolean execIf(String scenes) {
        if (scenes.contains(" and ")) {
            String[] andScene = scenes.split("and");
            return execIfSingle(andScene[0]) && execIfSingle(andScene[1]);
        } else if (scenes.contains(" or ")) {
            String[] orScenes = scenes.split("or");
            return execIfSingle(orScenes[0]) || execIfSingle(orScenes[1]);
        } else {
            return execIfSingle(scenes);
        }
    }

    /**
     * 执行单个判断语句
     * @param ifscenes 判断语句
     * @return treu:false
     */
    private static boolean execIfSingle(String ifscenes) {
        String key;
        String opt;
        String value;

        String re1 = "(\\$)";    // Any Single Character 1
        String re2 = "((?:[a-z][a-z0-9_]*))";    // Variable Name 1
        String re3 = "( )";    // Any Single Character 2
        String re4 = "((?:[a-z][a-z0-9_]*))";    // Variable Name 2
        String re5 = ".*?";    // Non-greedy match on filler
        String re6 = "((?:[a-z][a-z0-9_]*))";    // Variable Name 3

        Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(ifscenes);
        if (m.find()) {
            String c1 = m.group(1);
            key = m.group(2);
            String c2 = m.group(3);
            opt = m.group(4);
            value = m.group(5);
//            System.out.print("(" + c1.toString() + ")" + "(" + var1.toString() + ")" + "(" + c2.toString() + ")" + "(" + var2.toString() + ")" + "(" + var3.toString() + ")" + "\n");
            if ("is".equals(opt)) {
                if (value.equals(ReadStoryData.readGameParameter().get(key).toString())) {
                    return true;
                }else {
                    return false;
                }
            } else if ("gte".equals(opt)) {
                if (Integer.valueOf(ReadStoryData.readGameParameter().get(key).toString()) >= Integer.valueOf(value)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 设置参数
     *
     * @param scenes 设置参数语句
     */
    private static void setParam(String scenes) {
        if (scenes.contains("- 1")) {
            String re1="(\\$)";	// Any Single Character 1
            String re2="((?:[a-z][a-z0-9_]*))";	// Variable Name 1
            String re3="( )";	// Any Single Character 2
            String re4="(=)";	// Any Single Character 3
            String re5="( )";	// Any Single Character 4
            String re6="(\\$)";	// Any Single Character 5
            String re7="((?:[a-z][a-z0-9_]*))";	// Variable Name 2
            String re8=".*?";	// Non-greedy match on filler
            String re9="(\\d+)";	// Integer Number 1

            Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = p.matcher(scenes);
            if (m.find())
            {
                String c1=m.group(1);
                String var1=m.group(2);
                String c2=m.group(3);
                String c3=m.group(4);
                String c4=m.group(5);
                String c5=m.group(6);
                String var2=m.group(7);
                String int1=m.group(8);
                System.out.print("("+c1.toString()+")"+"("+var1.toString()+")"+"("+c2.toString()+")"+"("+c3.toString()+")"+"("+c4.toString()+")"+"("+c5.toString()+")"+"("+var2.toString()+")"+"("+int1.toString()+")"+"\n");
                Map<String, String> gameParamMap = ReadStoryData.readGameParameter();
                int value = Integer.valueOf(gameParamMap.get(var1).toString()) - 1;
                gameParamMap.put(var1, String.valueOf(value));
            }
        } else {
            String re1="(\\$)";	// Any Single Character 1
            String re2="((?:[a-z][a-z0-9_]*))";	// Variable Name 1
            String re3="( )";	// Any Single Character 2
            String re4="(=)";	// Any Single Character 3
            String re5="( )";	// Any Single Character 4
            String re6="((?:[a-z][a-z0-9_]*))";	// Variable Name 2

            Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = p.matcher(scenes);
            if (m.find())
            {
                String c1=m.group(1);
                String var1=m.group(2);
                String c2=m.group(3);
                String c3=m.group(4);
                String c4=m.group(5);
                String var2=m.group(6);
                System.out.print("("+c1.toString()+")"+"("+var1.toString()+")"+"("+c2.toString()+")"+"("+c3.toString()+")"+"("+c4.toString()+")"+"("+var2.toString()+")"+"\n");
                Map<String, String> gameParamMap = ReadStoryData.readGameParameter();
                gameParamMap.put(var1, var2);
                ReadStoryData.writeGameParameter(JSON.toJSONString(gameParamMap));
            }
        }
    }
}
