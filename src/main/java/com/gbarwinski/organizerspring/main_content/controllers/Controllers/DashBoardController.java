package com.gbarwinski.organizerspring.main_content.controllers.Controllers;

import com.gbarwinski.organizerspring.main_content.DTO.TaskDTO;
import com.gbarwinski.organizerspring.main_content.model.Project;
import com.gbarwinski.organizerspring.main_content.model.Sprint;
import com.gbarwinski.organizerspring.main_content.model.User;
import com.gbarwinski.organizerspring.main_content.services.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@ControllerAdvice
@Data
@RequiredArgsConstructor
@Controller
@Scope("session")
public class DashBoardController {
    private final ProgressServices progressServices;
    private final ProjectService projectService;
    private final TaskServices taskServices;
    private final SprintService sprintService;
    private final UserService userService;
    private final MessageServices messageServices;

    // -------------------------------------- dodane -----------------------------
    @GetMapping("/dashboard")
    public String showDashBoard(@RequestParam("id") Long projectId, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session.getAttribute("appUser") == null) return "redirect:/logout";
        Project actualProject = projectService.findProject(projectId);
        model.addAttribute("actualDashBoard", actualProject);

        model.addAttribute("taskList", taskServices.taskListByProjectId(projectId));
        model.addAttribute("usersAssignedToProject", userService.getAllUsersAssignedToProject(projectId));
        model.addAttribute("usersAssignedToProjectApartActiveUser", userService.getAllUsersAssignedToProjectApartActiveUser(projectId));
        session.setAttribute("actualDashBoard", actualProject);

        return "fragments_dashboard/dashBoard";
    }

    // -------------------------------------- dodane -----------------------------
    @GetMapping("/createTask")
    public String addCard(@RequestParam("id") Long projectId, Model model) {
        model.addAttribute("newTask", taskServices.newTaskDtoWithAssignedProjectId(projectId));
        model.addAttribute("usersAssignedToProject", userService.getAllUsersAssignedToProject(projectId));
        model.addAttribute("usersAssignedToProjectApartActiveUser", userService.getAllUsersAssignedToProjectApartActiveUser(projectId));
        return "fragments_dashboard/createTask";
    }

    // -------------------------------------- dodane -----------------------------
    @PostMapping("/createTask")
    public String postCard(@ModelAttribute("newTask") @Valid TaskDTO taskDTO, BindingResult bindingResult) {
        Long projectId = taskServices.getProjectIdFromTaskDTO(taskDTO);
        if (bindingResult.hasErrors()) {
            return "fragments_dashboard/createTask";
        }
        taskServices.createTaskUsingTaskDTO(taskDTO);
        return "redirect:/dashboard?id=" + projectId;
    }

    // -------------------------------------- dodane -----------------------------
    @GetMapping("/edittask")
    public String editCard(@RequestParam("id") Long id, Model model) {
        TaskDTO taskDTO = taskServices.getTaskDtoFromTask(id);
        model.addAttribute("oldTask", taskDTO);
        Long projectId = taskServices.getProjectIdFromTaskDTO(taskDTO);
        model.addAttribute("usersAssignedToProject", userService.getAllUsersAssignedToProject(projectId));
        model.addAttribute("usersAssignedToProjectApartActiveUser", userService.getAllUsersAssignedToProjectApartActiveUser(projectId));
        return "fragments_dashboard/editTask";
    }

    // -------------------------------------- dodane -----------------------------
    @PostMapping("/edittask")
    public String editCard(@ModelAttribute("oldTask") TaskDTO taskDTO, @RequestParam("id") Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fragments_dashboard/editTask";
        }

        taskServices.updateTaskUsingDTO(taskDTO, id);

        return "redirect:/dashboard?id=" + taskDTO.getProject().getId();

    }

    // -------------------------------------- dodane -----------------------------
    @GetMapping("/deletetask")
    public String deleteCard(@RequestParam("id") Long id) {
        Long projectId = taskServices.getProjectIdFromTask(id);
        taskServices.deleteTask(id);
        return "redirect:/dashboard?id=" + projectId;
    }

    // -------------------------------------- dodane -----------------------------
    @ModelAttribute
    public void AddAttributes(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User appUser = null;
        Project actualProject = null;
        if (session != null)
            appUser = (User) session.getAttribute("appUser");
        actualProject = (Project) session.getAttribute("actualDashBoard");

        if (appUser != null) {
            model.addAttribute("ActualUser", appUser);
            model.addAttribute("ActualUserInitialLetters", userService.getInitialLetters(appUser));
            model.addAttribute("actualDashBoard", actualProject);

            model.addAttribute("progress_steps", progressServices.findAllProgress());
            model.addAttribute("projectList", projectService.getAllProjectsForUser());

            model.addAttribute("sprintList", sprintService.findAll());
            model.addAttribute("sprint", new Sprint());

            model.addAttribute("taskList", taskServices.taskListByProjectId(actualProject));

            model.addAttribute("userList", userService.getAllUsersApartActiveUser());

            model.addAttribute("logsAboutProjects", messageServices.getLast5MessagesForActiveUser(appUser.getIdUser()));

        }

    }

}
