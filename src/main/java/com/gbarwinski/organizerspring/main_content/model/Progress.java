package com.gbarwinski.organizerspring.main_content.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
@AllArgsConstructor
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public Progress() {
    }
    public Progress(String name){
        this.name=name;
    }
}
