package com.crimps.utils;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.Map;

/**
 * Created by crimps on 2017/6/13.
 */
public class FileUtil {
    /**
     * 添加BOM开头
     * @param content
     * @return
     */
    public static String addBOM(String content) {
        try {
            byte[] bomBytes = new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF};
            byte[] contentBytes = content.getBytes("UTF-8");
            byte[] reply = new byte[bomBytes.length + contentBytes.length];
            System.arraycopy(bomBytes, 0, reply, 0, bomBytes.length);
            System.arraycopy(contentBytes, 0, reply, bomBytes.length, contentBytes.length);
            return new String(reply, "UTF-8");
        } catch (Exception e) {
            return content;
        }
    }

    /**
     * 将文本文件中的内容读入到buffer中
     * @param buffer buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(new File(filePath));
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath), "utf8"));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    /**
     * 将文本内容转以map格式
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static Map<String, String> readToMap(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        readToBuffer(sb, filePath);
        return (Map<String, String>) JSON.parse(sb.toString());
    }

    /**
     * 将文本内容写入文件：覆盖写
     * @param content 文本内容
     * @param filePath 文件路径
     */
    public static boolean writeToFile(String content, String filePath) {
        boolean flag = false;
        try {
            File file = new File(filePath);
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            // 往文件里写入字符串
            ps.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * utf-8 转unicode
     *
     * @param inStr
     * @return String
     */
    public static String utf8ToUnicode(String inStr) {
        char[] myBuffer = inStr.toCharArray();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inStr.length(); i++) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(myBuffer[i]);
            if (ub == Character.UnicodeBlock.BASIC_LATIN) {
                sb.append(myBuffer[i]);
            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                int j = (int) myBuffer[i] - 65248;
                sb.append((char) j);
            } else {
                short s = (short) myBuffer[i];
                String hexS = Integer.toHexString(s);
                hexS = hexS.replaceAll("ffff", "");
                String unicode = "\\u" + hexS;
                sb.append(unicode.toLowerCase());
            }
        }
        return sb.toString();
    }
}
