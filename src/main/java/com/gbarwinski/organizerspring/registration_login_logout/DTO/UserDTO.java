package com.gbarwinski.organizerspring.registration_login_logout.DTO;

import com.gbarwinski.organizerspring.registration_login_logout.validator.PasswordMatches;
import com.gbarwinski.organizerspring.registration_login_logout.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class UserDTO {
    @NotBlank(message="Pole z imieniem nie może być puste")
    @Size( max=30,message="Pole z imieniem nie może zawierać więcej niż 30 znaków")
    @Pattern(regexp="[a-zA-Z]+",message="Pole z imieniem musi zawierac wyłącznie litery")
    private String firstName;

    @NotBlank(message="Pole z nazwiskiem nie może być puste")
    @Size( max=30,message="Pole z nazwiskiem nie może zawierać więcej niż 30 znaków")
    @Pattern(regexp="[a-zA-Z]+",message="Pole z nazwiskiem musi zawierac wyłącznie litery")
    private String secondName;

    @NotBlank(message="Pole zawierające nick nie może być puste")
    @Size( max=30,message="Nick nie może zawierać więcej niż 30 znaków")
    private String nick;

    @ValidPassword(message="Hasło nie spełnia wymagań")
    @NotNull(message="Pole zawierające hasło nie może być puste")
    private String password;

    @NotNull(message="Pole zawierające hasło nie może być puste")
    private String matchingPassword;

    @NotBlank(message="Pole zawierające email nie może być puste")
    @Email (message="Email nie spełnia wymagań")
    @Size(max=50,message="Email nie może zawierać więcej niż 50 znaków")
    @Column(unique = true)
    private String email;

    private Roles roles;
}
