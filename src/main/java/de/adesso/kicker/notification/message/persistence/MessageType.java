package de.adesso.kicker.notification.message.persistence;

public enum MessageType {

    MESSAGE_DECLINED("notification.message.declined");

    private String messageContent;

    private MessageType(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageContent() {
        return this.messageContent;
    }
}