package org.jeedevframework.springboot.message.redis.queue;

import java.io.Serializable;

public class DataMessage implements Serializable {

	private  String content;

	
	public DataMessage() {
		super();
	}

	public DataMessage(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
