package com.gbarwinski.organizerspring.main_content.controllers.Controllers;

import com.gbarwinski.organizerspring.main_content.DTO.ProjectDTO;
import com.gbarwinski.organizerspring.main_content.Utills.UtillClass;
import com.gbarwinski.organizerspring.main_content.model.User;
import com.gbarwinski.organizerspring.main_content.services.MessageServices;
import com.gbarwinski.organizerspring.main_content.services.ProjectService;
import com.gbarwinski.organizerspring.main_content.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProjectsController {

    private final ProjectService projectService;
    private final UserService userService;
    private final MessageServices messageServices;
// -------------------------- zrobione
    @GetMapping("/projects")
    public String showProjects(Model model, HttpServletRequest request) {
        return "fragments_projects/browserProject";
    }
    // -------------------------- zrobione
    @GetMapping("/createProject")
    public String createProject(Model model) {
        model.addAttribute("newProject", new ProjectDTO());
        return "fragments_projects/addProject";
    }
    // -------------------------- zrobione
    @PostMapping("/createProject")
    public String createProject(@ModelAttribute("newProject") @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments_projects/addProject";
        }
        projectService.createProject(projectDTO);
        return "redirect:/projects";
    }
    // -------------------------- zrobione
    @GetMapping("/editproject")
    public String editProject(@RequestParam("id") Long projectId, Model model) {

        model.addAttribute("oldProject", projectService.findProjectAndTransferToDTO(projectId));
        model.addAttribute("usersAssignedToProject", userService.getAllUsersAssignedToProject(projectId));

        return "fragments_projects/editProject";
    }
    // -------------------------- zrobione
    @PostMapping("/editproject")
    public String editProject(Model model, @ModelAttribute("oldProject") ProjectDTO projectDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments_projects/addProject";
        }
        projectService.updateProject(projectDTO);
        return "redirect:/projects";
    }

    // -------------------------- zrobione
    @GetMapping("/deleteProject")
    public String deleteProject(@RequestParam("id") Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
    // -------------------------- zrobione
    @ModelAttribute
    public void AddAttributes(Model model, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        User appUser = null;
        if (session != null) appUser = (User) session.getAttribute("appUser");

        if (appUser != null) {
            model.addAttribute("avatarList", UtillClass.getListOfIconTitles());
            model.addAttribute("allAdminsInitialsList", projectService.getProjectAdminNameAndSurname());
            model.addAttribute("logsAboutProjects",messageServices.getLast5MessagesForActiveUser(appUser.getIdUser()));
        }
    }
}
