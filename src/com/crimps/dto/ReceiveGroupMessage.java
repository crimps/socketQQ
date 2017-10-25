package com.crimps.dto;

/**
 * 群消息类
 *
 * @author crimps
 * @create 2017-10-24 15:13
 **/
public class ReceiveGroupMessage extends ReceiveBaseMessage {
    /**
     *
     *示例：
     *{
     * "username": "泰兰德",
     * "nick": "泰奶奶",
     * "sex": "0",
     * "age": "12000",
     * "error": "0",
     * "act": "2",
     * "fromGroup": "1234",
     * "fromGroupName": "月之女祭司",
     * "fromQQ": "1234",
     * "subType": "1",
     * "sendTime": "1481481775",
     * "fromAnonymous": "",
     * "msg": "谁看到玛法里奥了？",
     * "font": "7141560"
     * }
     */
    public String fromGroup;
    public String fromAnonymous;
    public String username;
    public String fromGroupName;

    public String getFromGroup() {
        return fromGroup;
    }

    public void setFromGroup(String fromGroup) {
        this.fromGroup = fromGroup;
    }

    public String getFromAnonymous() {
        return fromAnonymous;
    }

    public void setFromAnonymous(String fromAnonymous) {
        this.fromAnonymous = fromAnonymous;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFromGroupName() {
        return fromGroupName;
    }

    public void setFromGroupName(String fromGroupName) {
        this.fromGroupName = fromGroupName;
    }
}
