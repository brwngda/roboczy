package com.gbarwinski.organizerspring.main_content.model.repositories;

import com.gbarwinski.organizerspring.main_content.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
List<Message> findAllByProjectId(Long projectId);
}
