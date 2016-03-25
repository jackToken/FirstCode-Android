package com.firstcode.vo;

/**
 * Created by wangjinliang on 2016/3/4.
 */
public class TalkItem {
    public static final int MSG_RECEIVER = 0;
    public static final int MSG_SNED = 1;
    private String content;
    private int type;

    public TalkItem(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
