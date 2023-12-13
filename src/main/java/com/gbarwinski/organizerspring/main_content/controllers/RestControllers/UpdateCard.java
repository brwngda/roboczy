package com.gbarwinski.organizerspring.main_content.controllers.RestControllers;

import com.gbarwinski.organizerspring.main_content.model.Task;
import com.gbarwinski.organizerspring.main_content.services.TaskServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UpdateCard {
    private final TaskServices taskServices;
// -------------------------------- zrobione
    @GetMapping("/task/{taskId}/{progress}")
    public void updateCardProgress(@PathVariable("taskId") Long taskId, @PathVariable("progress") String progress) {
        Task task = taskServices.findTask(taskId);
        task.setProgress(progress);
        taskServices.saveTask(task);
    }



}
