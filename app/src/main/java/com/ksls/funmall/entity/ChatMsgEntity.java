
package com.ksls.funmall.entity;

public class ChatMsgEntity {

    private String head;

    private String date;

    private String text;
    
    private String time;

    private boolean isComMeg = true;

    public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
    	isComMeg = isComMsg;
    }
}
