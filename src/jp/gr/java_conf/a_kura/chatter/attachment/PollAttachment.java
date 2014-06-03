package jp.gr.java_conf.a_kura.chatter.attachment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PollAttachment extends Attachment {

	private String attachmentType = "Poll";
	private List<String> pollChoices;

	public PollAttachment() {
		pollChoices = new ArrayList<String>();
	}
	
	public PollAttachment(String[] pollChoices) {
		this.pollChoices = Arrays.asList(pollChoices);
	}

	@Override
	public String getAttachmentType() {
		return attachmentType;
	}

	public void addPollChoice(String poll) {
		pollChoices.add(poll);
	}

	public String[] getPollChoices() {
		return pollChoices.toArray(new String[0]);
	}
}
