package com.gbarwinski.organizerspring.main_content.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectDTO {
    private Long id;
    @NotNull(message = "Nazwa projektu nie może być pusta")
    @Size(min=2, max=20,message = "Nazwa projektu nie może mieć więcej niż 20 znaków ")
    private String name;
    @Size(max=25,message = "Opis projektu nie może mieć więcej niż 25 znaków ")
    private String description;
    @NotBlank(message = "Musisz wybrać avatar")
    private String avatar;
    private String admin;
    private boolean isStarred;
}
