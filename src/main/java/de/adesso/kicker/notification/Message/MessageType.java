package de.adesso.kicker.notification.Message;

public enum MessageType {

    MESSAGE_DECLINED("notification.message.declined");

    String messageType;

    private MessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return this.messageType;
    }
}