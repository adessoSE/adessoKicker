package de.adesso.kicker.notification.message.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {
    MESSAGE_DECLINED("notification.message.declined");

    private final String messageContent;
}