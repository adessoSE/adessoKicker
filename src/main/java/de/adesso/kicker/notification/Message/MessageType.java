package de.adesso.kicker.notification.Message;

public enum MessageType {

    MESSAGE_DECLINED("notification.message.declined");

    private String messageContent;

    private MessageType(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageType() {
        return this.messageContent;
    }
}