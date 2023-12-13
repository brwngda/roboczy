package com.gbarwinski.organizerspring.registration_login_logout.validator;

import com.gbarwinski.organizerspring.main_content.model.User;
import com.gbarwinski.organizerspring.registration_login_logout.DTO.UserDTO;

import java.io.IOException;

public interface IUserService {
    User registerNewUserAccount(UserDTO accountDto)
            throws IOException;
}
