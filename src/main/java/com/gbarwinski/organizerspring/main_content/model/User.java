package com.gbarwinski.organizerspring.main_content.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gbarwinski.organizerspring.registration_login_logout.DTO.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String name;

    private String surname;

    private String nick;

    private String email;
    private String password;

    @ManyToMany (mappedBy = "users")
    @JsonIgnore
    private List<Project> projects;

    @OneToOne (mappedBy = "user")
    @JsonIgnore
    private Task task;

    private String href;

    @Enumerated
    public Roles role;

    public User(String name, String surname,  @NotNull String email, @NotNull String password,  @NotNull String nick, Roles role) {
        this.name = name;
        this.surname = surname;
        this.nick = nick;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(List<Project> projects) {
        this.projects = projects;
    }

    public User(Task task) {
        this.task = task;
    }

    public User(String href) {
        this.href=href;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idUser, user.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }
}
