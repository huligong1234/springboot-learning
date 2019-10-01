package org.jeedevframework.springboot.message;

public interface IMessageSender {
    void sendMessage(String channel, String message);
}
