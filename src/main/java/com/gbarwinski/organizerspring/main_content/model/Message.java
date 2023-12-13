package com.gbarwinski.organizerspring.main_content.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MessageId;
    private Long taskId;
    private Long projectId;
    private String message;
    private Long userId;


    public Message(Long taskId, Long projectId, String message, Long userId) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.message = message;
        this.userId = userId;

    }

    public Message() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(MessageId, message.MessageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MessageId);
    }
}
