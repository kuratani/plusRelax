package jp.gr.java_conf.a_kura.chatter.message;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private List<MessageSegment> messageSegments = new ArrayList<MessageSegment>();

    public void addMessageSegment(MessageSegment segment) {
    	messageSegments.add(segment);
    }

    public List<MessageSegment> getMessageSegments() {
        return messageSegments;
    }

}
