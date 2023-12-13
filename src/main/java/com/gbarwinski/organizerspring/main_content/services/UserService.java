package com.gbarwinski.organizerspring.main_content.services;

import com.gbarwinski.organizerspring.main_content.Utills.UtillClass;
import com.gbarwinski.organizerspring.main_content.model.Project;
import com.gbarwinski.organizerspring.main_content.model.User;
import com.gbarwinski.organizerspring.main_content.model.repositories.ProjectRepository;
import com.gbarwinski.organizerspring.main_content.model.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public User findUserById(Long id) {
        Optional<User> UserbyId = userRepository.findById(id);
        if (UserbyId.isPresent()) {
            return UserbyId.get();
        } else return new User();
    }
    public boolean exists(String email) {
        User userByEmail = userRepository.findByEmail(email);
        if (userByEmail!=null) {
            return true;
        } else return false;
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUsersApartActiveUser() {
        List<User> all = getAllUsers();
        Long activeUserId = getActiveUserId();
        return all.stream()
                .filter(user -> !user.getIdUser().equals(activeUserId))
                .collect(Collectors.toList());
    }


    public List<User> getAllUsersAssignedToProject(Long projectId) {
        List<User> all = getAllUsers();
        Optional<Project> ProjectById = projectRepository.findById(projectId);

        List<User> collect = new ArrayList<>();
        if (ProjectById.isPresent()) {
            collect = all.stream()
                    .filter(user -> user.getProjects().stream().
                            anyMatch(project -> project.getId() == projectId))
                    .distinct()
                    .collect(Collectors.toList());
        }
        return collect;
    }
   public List<User> getAllUsersAssignedToProjectApartActiveUser(Long projectId){
       List<User> allUsersAssignedToProject = getAllUsersAssignedToProject(projectId);
       allUsersAssignedToProject.remove(UtillClass.getLoggedInUser());
       return allUsersAssignedToProject;
   }


    private Long getActiveUserId() {

        return UtillClass.getLoggedInUser().getIdUser();
    }


    public String getInitialLetters(User user) {
        String initials = user.getName().toUpperCase().substring(0, 1) + user.getSurname().toUpperCase().substring(0, 1);
        return initials;
    }

}

