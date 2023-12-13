package com.gbarwinski.organizerspring.main_content.services;

import com.gbarwinski.organizerspring.main_content.DTO.TaskDTO;
import com.gbarwinski.organizerspring.main_content.model.Project;
import com.gbarwinski.organizerspring.main_content.model.Task;
import com.gbarwinski.organizerspring.main_content.model.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskServices {
    private final SprintService sprintService;
    private final TaskRepository taskRepository;
    private final ProgressServices progressServices;
    private final ProjectService projectService;

    // zrobione
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void createTaskUsingTaskDTO(TaskDTO taskDTO) {
        Task task = new Task();
        getTaskFromTaskDTO(taskDTO, task);
        task.setProgress(progressServices.findAllProgress().get(0).getName());
        saveTask(task);
    }
    // zrobione
    public Task findTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Brak takiego zadania"));
    }
    // zrobione
    private Task getTaskFromTaskDTO(TaskDTO taskDTO, Task task) {
        task.setProject(projectService.findProject(taskDTO.getProject().getId()));
        task.setDescription(taskDTO.getDescription());
        task.setName(taskDTO.getName());
        task.setPriority(taskDTO.getPriority());
        task.setTypeOfStory(taskDTO.getTypeOfStory());
        task.setSprint(taskDTO.getSprint());
        task.setStoryPoints(taskDTO.getStoryPoints());
        task.setUser(taskDTO.getUser());
        return task;
    }


    public void updateTaskUsingDTO(TaskDTO taskDto, Long id) {
        Task task = findTask(id);
        getTaskFromTaskDTO(taskDto, task);
        if (taskDto.getProgress() != null) task.setProgress(taskDto.getProgress());
        saveTask(task);
    }

// zrobione
    public TaskDTO getTaskDtoFromTask(Long id) {
        Task oldTask = findTask(id);
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setIdTask(oldTask.getIdTask());
        taskDTO.setDescription(oldTask.getDescription());
        taskDTO.setName(oldTask.getName());
        if (oldTask.getProgress() != null) taskDTO.setProgress(oldTask.getProgress());
        taskDTO.setPriority(oldTask.getPriority());
        taskDTO.setTypeOfStory(oldTask.getTypeOfStory());
        taskDTO.setProject(projectService.findProject(oldTask.getProject().getId()));
        taskDTO.setSprint(oldTask.getSprint());
        taskDTO.setStoryPoints(oldTask.getStoryPoints());
        taskDTO.setUser(oldTask.getUser());
        return taskDTO;
    }

    public List<Task> taskListBySprintId(Long id) {
        return taskRepository.findAllBySprint(sprintService.findById(id)).orElseThrow(() -> new IllegalArgumentException("brak zadan"));
    }

    public List<Task> taskListByProjectId(Project project) {

        if (project != null) {
            Optional<List<Task>> AllTasksByProjectName = taskRepository.findAllByProjectId(project.getId());
            return AllTasksByProjectName.orElse(new ArrayList<Task>());
        } else return new ArrayList<Task>();
    }

    public List<Task> taskListByProjectId(Long id) {
        Optional<List<Task>> AllTasksByProjectName = taskRepository.findAllByProjectId(id);
        return AllTasksByProjectName.orElse(new ArrayList<Task>());

    }

    public void deleteTask(Long id) {
        Task task = findTask(id);
        taskRepository.delete(task);
    }

    public TaskDTO newTaskDtoWithAssignedProjectId(Long projectId) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setProject(projectService.findProject(projectId));
        return taskDTO;
    }

    public Long getProjectIdFromTaskDTO(TaskDTO taskDTO) {
        return taskDTO.getProject().getId();
    }

    public Long getProjectIdFromTask(Long id) {
        Task task = findTask(id);
        return task.getProject().getId();
    }

}
