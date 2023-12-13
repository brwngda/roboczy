package com.gbarwinski.organizerspring.main_content.model.repositories;

import com.gbarwinski.organizerspring.main_content.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {
}
