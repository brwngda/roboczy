package com.gbarwinski.organizerspring.hibernate;

import com.gbarwinski.organizerspring.main_content.model.Task;
import com.gbarwinski.organizerspring.main_content.model.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@RequiredArgsConstructor
public class TaskRepositoryUnitTest {

    @Autowired
    private  TaskRepository taskRepository;

    @Test
    public void whenFindByName_thenReturnTask(){

        //given
        Task task = new Task("Zadanie testowe");
        taskRepository.save(task);
        //when
        Task found = taskRepository.findByName(task.getName());
        //then
        assertThat(found.getName()).isEqualTo(task.getName());
    }

}
