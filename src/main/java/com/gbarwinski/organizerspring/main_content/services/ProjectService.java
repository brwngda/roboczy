package com.gbarwinski.organizerspring.main_content.services;

import com.gbarwinski.organizerspring.main_content.DTO.ProjectDTO;
import com.gbarwinski.organizerspring.main_content.Utills.UtillClass;
import com.gbarwinski.organizerspring.main_content.model.Project;
import com.gbarwinski.organizerspring.main_content.model.User;
import com.gbarwinski.organizerspring.main_content.model.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectService {


    private final ProjectRepository projektRepository;
    private final UserService userService;

    // zrobione
    public Project findProject(Long projectId) {
        Optional<Project> projekt = projektRepository.findById(projectId);
        Project project1 = new Project();
        if (projekt.isPresent()) {
            project1 = projekt.get();
        }
        return project1;
    }

    public List<Project> findAllProjectForUser(Long userId) {
        User activeUser = userService.findUserById(userId);
        return filterProjectsForUser(activeUser);
    }

    public List<Project> getAllProjectsForUser() {
        User activeUser = UtillClass.getLoggedInUser();
        return filterProjectsForUser(activeUser);
    }

    public List<Project> filterProjectsForUser(User activeUser) {
        List<Project> allProjects = projektRepository.findAll();
        return allProjects.stream()
                .filter(project -> project.getUsers().stream().
                        anyMatch(userx -> userx.getIdUser() == activeUser.getIdUser()))
                .collect(Collectors.toList());
    }


    public List<User> getProjectAdminNameAndSurname() {
        return getAllProjectsForUser().stream()
                .map(admin -> (userService.findUserByEmail(admin.getAdmin())))
                .distinct()
                .collect(Collectors.toList());
    }


    public ProjectDTO findProjectAndTransferToDTO(Long id) {
        Project project = findProject(id);
        return transformProjectToProjectDTO(project);
    }

    private ProjectDTO transformProjectToProjectDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setStarred(project.isStarred());
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setAdmin(project.getAdmin());
        projectDTO.setAvatar(project.getAvatar());
        return projectDTO;
    }

    private Project transformProjectDtoToProject(Project project, ProjectDTO projectDTO) {
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setAdmin(projectDTO.getAdmin());
        project.setAvatar(projectDTO.getAvatar());
        project.setStarred(project.isStarred());
        return project;
    }

    public void createProject(ProjectDTO projectDTO) {
        ArrayList<User> users = new ArrayList<>();
        users.add(UtillClass.getLoggedInUser());
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription(), projectDTO.getAvatar(), users,UtillClass.getLoggedInUser().getEmail());
        save(project);
    }

    public void updateProject(ProjectDTO projectDTO) {
        Long projectId = projectDTO.getId();
        Project project = findProject(projectId);
        Project updatedProject = transformProjectDtoToProject(project, projectDTO);
        save(updatedProject);

    }


    public void deleteProject(Long id) {
        Project project = findProject(id);
        projektRepository.delete(project);
    }

    public void addUserToProject(Long idProject, Long idUser) {
        Optional<Project> ProjectById = projektRepository.findById(idProject);
        User userByEmail = userService.findUserById(idUser);
        if (ProjectById.isPresent()) {
            Project project = ProjectById.get();
            List<User> users = project.getUsers();
            if (!users.contains(userByEmail)) {
                users.add(userByEmail);
                project.setUsers(users);
                save(project);
            }
        }
    }

    public Project addInitialProject(User user) {
        List<User> users = new ArrayList<>();
        users.add(user);
        Project initialProject = new Project("Sample project", "Project description", user.getEmail(), "icons/015.png", users, false);
        projektRepository.save(initialProject);
        return initialProject;
    }

    public void save(Project project) {
        projektRepository.save(project);
    }

}
