package com.crimps.dto;

/**
 * 消息类常量
 *
 * @author crimps
 * @create 2017-10-25 10:22
 **/
public class Message {

    /**
     * 群消息
     */
    public final static String REC_SUBTYPE_GROUP ="1";

    /**
     * 讨论组消息
     */
    public final static String REC_SUBTYPE_DISCUSSION = "4";

    /**
     * 私聊消息 来自好友
     */
    public final static String REC_SUBTYPE_DIRECT_FRIEND = "11";

    /**
     * 私聊消息 来自在线状态
     */
    public final static String REC_SUBTYPE_DIRECT_ONLINE = "1";

    /**
     * 私聊消息 来自群
     */
    public final static String REC_SUBTYPE_DIRECT_GROUP = "2";

    /**
     * 私聊消息 来自讨论组
     */
    public final static String REC_SUBTYPE_DIRECT_DISCUSSION = "3";

    /**
     * 发送私信消息
     */
    public final static String SEND_DIRECT = "106";

    /**
     * 发送讨论组消息
     */
    public final static String SEND_DISCUSSION = "103";

    /**
     * 发送群消息
     */
    public final static String SEND_GROUP = "101";

    /**
     * 消息类型key值
     */
    public static final String MESSAGE_SUBTYPE = "subType";
}
