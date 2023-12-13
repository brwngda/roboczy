package com.gbarwinski.organizerspring.main_content.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table
@Entity
@NoArgsConstructor
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSprint;
    private String name;
    private Date start;
    private Date end;
    private Long storyPointsGranted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sprint")
    @JsonIgnore
    private List<Task> tasks;

}
