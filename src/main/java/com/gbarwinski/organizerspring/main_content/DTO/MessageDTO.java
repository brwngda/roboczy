package com.gbarwinski.organizerspring.main_content.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageDTO implements Serializable {
    private Long taskId;
    private String message;
    private Long userId;
    private String taskName;

    public MessageDTO(Long taskId, String message, Long userId, String taskName) {
        this.taskId = taskId;
        this.message = message;
        this.userId = userId;
        this.taskName = taskName;
    }

    public MessageDTO() {
    }

    public MessageDTO(Long taskId, Long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }
}
