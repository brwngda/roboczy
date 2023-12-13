package com.gbarwinski.organizerspring.main_content.DTO;

import com.gbarwinski.organizerspring.main_content.model.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TaskDTO {

    private Long idTask;

    @NotBlank(message = "Nazwa zadania nie może być pusta")
    @Size(min = 2, max = 50, message = "Nazwa zadania powinna zawierać od 2 do 50 znaków")
    private String name;
    @Size(max = 250, message = "Opis zadania nie może mieć więcej niż 250 znaków")
    private String description;
    private Sprint sprint;
    private Project project;
    private Priority priority;
    private Long storyPoints;
    private TypeOfStory typeOfStory;
    private String progress;
    private User user;

}
