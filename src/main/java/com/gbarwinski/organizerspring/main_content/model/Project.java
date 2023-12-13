package com.gbarwinski.organizerspring.main_content.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String admin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private List<Task> tasks;
    @ManyToMany(fetch=FetchType.EAGER)
    @JsonIgnore
    private List<User> users;
    private String avatar;
    private boolean isStarred=false;

    public Project(String name, String description,String avatar,List<User> users,String admin) {
        this.name=name;
        this.description=description;
        this.avatar= avatar;
        this.users=users;
        this.admin=admin;
    }
    public Project(String name, String description,String admin, String avatar,List<User> users,boolean isStarred) {
        this.name=name;
        this.description=description;
        this.admin= admin;
        this.avatar= avatar;
        this.users=users;
        this.isStarred=isStarred;
    }
    public Project(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", admin='" + admin + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isStarred=" + isStarred +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
