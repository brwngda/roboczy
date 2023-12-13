package com.gbarwinski.organizerspring.main_content.services;

import com.gbarwinski.organizerspring.main_content.model.Progress;
import com.gbarwinski.organizerspring.main_content.model.repositories.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProgressServices {
    private final ProgressRepository progressRepository;

    public List<Progress> findAllProgress() {
        List<Progress> allProjectProgress = progressRepository.findAll();
        boolean empty = allProjectProgress.isEmpty();
        LinkedHashMap<Integer, Progress> linkedMap = new LinkedHashMap<>();
        if (empty) {
            linkedMap.put(1, new Progress("TO DO"));
            linkedMap.put(2, new Progress("PREPARED FOR IMPLEMENTATION"));
            linkedMap.put(3, new Progress("IN PROGRESS"));
            linkedMap.put(4, new Progress("IN REVIEW"));
            linkedMap.put(5, new Progress("APPROVED"));
            for (Map.Entry<Integer, Progress> integerProgressEntry : linkedMap.entrySet()) {
                progressRepository.save(integerProgressEntry.getValue());
            }
            ;
        }
        return allProjectProgress;
    }


}
