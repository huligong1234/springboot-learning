package org.jeedevframework.springboot.message;

public interface IMessageReceiver {
    String DEFAULT_LISTENER_METHOD = "receiveMessage";
    
    void receiveMessage(String message);
}
