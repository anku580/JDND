package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {

    private MessageType type;
    private String content;
    private String sender;
    private int onlineUsers;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        SPEAK
    }

    public Message() {
    }

    public Message(MessageType type, String content, String sender, int onlineUsers) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.onlineUsers = onlineUsers;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(int onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
}
