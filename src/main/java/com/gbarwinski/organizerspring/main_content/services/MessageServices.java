package com.gbarwinski.organizerspring.main_content.services;

import com.gbarwinski.organizerspring.main_content.DTO.MessageDTO;
import com.gbarwinski.organizerspring.main_content.model.Message;
import com.gbarwinski.organizerspring.main_content.model.Task;
import com.gbarwinski.organizerspring.main_content.model.User;
import com.gbarwinski.organizerspring.main_content.model.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageServices {
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final TaskServices taskServices;
    private final ProjectService projectService;


    public List<Message> getLast5MessagesForActiveUser(Long userId) {

        List<Long> allProjectIdsForUser = projectService.findAllProjectForUser(userId).stream()
                .map(project -> project.getId())
                .collect(Collectors.toList());

        List<Message> allMessages = messageRepository.findAll();
        return allMessages.stream()
                .filter(message -> (allProjectIdsForUser.stream().anyMatch(projectId -> message.getProjectId() == projectId)))
                .sorted(Comparator.comparing(Message::getMessageId).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }


    public Message saveAndGetReadyMessage(MessageDTO messageDTO, String update) {
        Message message = createMessageEntity(messageDTO, update);
        messageRepository.save(message);

        return message;
    }

    public String defineMessageText(ModyficationType modyficationType,String taskName, String projectName, String userEmail,String progress) {
        String message = "";
        switch (modyficationType) {
            case UPDATE:
                message= createUpdateMessage(taskName, projectName, userEmail);
                break;
            case UPDATE_PROGRESS:
                message= createUpdateProgressMessage(taskName, projectName, userEmail,progress);
                break;
            default:
                throw new IllegalArgumentException("The following modification does not exist");
        }
        return message;

    }

    public Message createMessageEntity(MessageDTO messageDTO, String update) {


        Long taskId = messageDTO.getTaskId();
        Long userId=messageDTO.getUserId();

        Task task = taskServices.findTask(taskId);
        String taskName = task.getName();
        String progress=task.getProgress();
        Long projectId = task.getProject().getId();
        String projectName = task.getProject().getName();
        String userEmail = userService.findUserById(userId).getEmail();

        String innerMessageText = defineMessageText(ModyficationType.valueOf(update),taskName, projectName, userEmail,progress);


        return new Message(taskId, projectId, innerMessageText, userId);

    }

    public String createUpdateMessage(String taskName, String projectName, String userEmail) {
        return String.format("User %s modified task (%s) in project %s", userEmail, taskName, projectName);
    }
    public String createUpdateProgressMessage(String taskName, String projectName, String userEmail,String progress) {
        return String.format("In project %s, User %s changed status in task %s to %s", projectName, userEmail, taskName, progress);
    }
    public Set<Long> getAssignedUsersToProject(MessageDTO messageDTO) {
        Long taskId = messageDTO.getTaskId();
        Long projectId = taskServices.getProjectIdFromTask(taskId);
        Set<Long> usersAssignedToProject = projectService.findProject(projectId).getUsers().stream()
                .map(User::getIdUser)
                .collect(Collectors.toSet());
        return usersAssignedToProject;
    }
}
