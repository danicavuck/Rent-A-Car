package com.group56.notificationservice.dto;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmailDTO {
    private String senderUsername;
    private String receiverUsername;
    private String content;
}
