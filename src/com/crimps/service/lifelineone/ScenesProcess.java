package com.crimps.service.lifelineone;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crimps on 2017/6/16.
 */
public class ScenesProcess {
    /**
     * 场景逻辑处理
     * @param scenesList 场景内容列表
     * @return
     */
    public static String processScenes(List<String> scenesList) {
        StringBuffer sb = new StringBuffer("");
        //场景逻辑处理
        List<String> logicedList = logicProcess(scenesList);
        //场景参数设置

        return sb.toString();
    }

    /**
     * 是否IF逻辑开始
     * @param content
     * @return
     */
    private static boolean isIFStart(String content) {
        if (content.contains("<<if")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否IF逻辑结束
     * @param content
     * @return
     */
    private static boolean isIFEnd(String content) {
        if (content.contains("<<endif>>")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 场景逻辑处理
     * @param scenesList 场景列表
     * @return
     */
    private static List<String> logicProcess(List<String> scenesList) {
        List<String> logicedScenesList = new ArrayList<String>();
        boolean inFlag = false;
        List<String> ifList = new ArrayList<String>();
        for (String scene : scenesList) {
            if (isIFStart(scene)) {
                inFlag = true;
            }
            if (isIFEnd(scene)) {
                ifList.add(scene);
                processIF(scenesList, ifList);
                ifList.clear();
                inFlag = false;
                continue;
            }
            if (inFlag) {
                ifList.add(scene);
            } else {
                logicedScenesList.add(scene);
            }
        }
        return logicedScenesList;
    }

    /**
     * IF逻辑处理
     * @param ifList
     * @return
     */
    private static List<String> processIF(List<String> List, List<String> ifList) {
        List<String> ifLogicedList = new ArrayList<String>();
        for (String scene : ifList) {
            matchIf(scene);
            matchElseIf(scene);
            matchELSE(scene);
        }
        return ifLogicedList;
    }

    /**
     * 匹配IF语句
     * @param content
     * @return
     */
    private static String matchIf(String content) {
        String re1="(<)";	// Any Single Character 1
        String re2="(<)";	// Any Single Character 2
        String re3="(if)";	// Word 1
        String re4="( )";	// White Space 1
        String re5="(\\$)";	// Any Single Character 3
        String re6="((?:[a-z][a-z]+))";	// Word 2
        String re7="( )";	// White Space 2
        String re8="((?:[a-z][a-z]+))";	// Word 3
        String re9="( )";	// White Space 3
        String re10="((?:[A-Za-z0-9]+))";	// Word 4
        String re11="(>)";	// Any Single Character 4
        String re12="(>)";	// Any Single Character 5

        Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9+re10+re11+re12,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(content);
        if (m.find()) {
            String c1 = m.group(1);
            String c2 = m.group(2);
            String word1 = m.group(3);
            String ws1 = m.group(4);
            String c3 = m.group(5);
            String word2 = m.group(6);
            String ws2 = m.group(7);
            String word3 = m.group(8);
            String ws3 = m.group(9);
            String word4 = m.group(10);
            String c4 = m.group(11);
            String c5 = m.group(12);
            System.out.print("(" + c1.toString() + ")" + "(" + c2.toString() + ")" + "(" + word1.toString() + ")" + "(" + ws1.toString() + ")" + "(" + c3.toString() + ")" + "(" + word2.toString() + ")" + "(" + ws2.toString() + ")" + "(" + word3.toString() + ")" + "(" + ws3.toString() + ")" + "(" + word4.toString() + ")" + "(" + c4.toString() + ")" + "(" + c5.toString() + ")" + "\n");
            return m.group(6);
        } else {
            return null;
        }
    }

    /**
     * 匹配ELSEIF语句
     * @param content
     * @return
     */
    private static String matchElseIf(String content) {
        String re1="(<)";	// Any Single Character 1
        String re2="(<)";	// Any Single Character 2
        String re3="(elseif)";	// Word 1
        String re4="( )";	// White Space 1
        String re5="(\\$)";	// Any Single Character 3
        String re6="((?:[a-z][a-z]+))";	// Word 2
        String re7="( )";	// White Space 2
        String re8="((?:[a-z][a-z]+))";	// Word 3
        String re9="( )";	// White Space 3
        String re10="((?:[a-z][a-z]+))";	// Word 4
        String re11="(>)";	// Any Single Character 4
        String re12="(>)";	// Any Single Character 5

        Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9+re10+re11+re12,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(content);
        if (m.find()) {
            String c1 = m.group(1);
            String c2 = m.group(2);
            String word1 = m.group(3);
            String ws1 = m.group(4);
            String c3 = m.group(5);
            String word2 = m.group(6);
            String ws2 = m.group(7);
            String word3 = m.group(8);
            String ws3 = m.group(9);
            String word4 = m.group(10);
            String c4 = m.group(11);
            String c5 = m.group(12);
            System.out.print("(" + c1.toString() + ")" + "(" + c2.toString() + ")" + "(" + word1.toString() + ")" + "(" + ws1.toString() + ")" + "(" + c3.toString() + ")" + "(" + word2.toString() + ")" + "(" + ws2.toString() + ")" + "(" + word3.toString() + ")" + "(" + ws3.toString() + ")" + "(" + word4.toString() + ")" + "(" + c4.toString() + ")" + "(" + c5.toString() + ")" + "\n");
            return m.group(5);
        } else {
            return null;
        }
    }

    /**
     * 匹配ELSE
     * @param content
     * @return
     */
    private static String matchELSE(String content) {
        if (content.contains("<<else>>")) {
            System.out.println(content);
            return "df";
        } else {
            return null;
        }
    }
}
