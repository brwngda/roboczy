package com.gbarwinski.organizerspring.main_content.model.repositories;

import com.gbarwinski.organizerspring.main_content.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress,Long> {
}
