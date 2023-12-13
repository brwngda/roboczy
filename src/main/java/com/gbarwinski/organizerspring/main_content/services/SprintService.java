package com.gbarwinski.organizerspring.main_content.services;

import com.gbarwinski.organizerspring.main_content.model.Sprint;
import com.gbarwinski.organizerspring.main_content.model.repositories.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SprintService {

    private final SprintRepository sprintRepository;

    public List<Sprint> findAll(){

        return  sprintRepository.findAll();
    }
    public Sprint findById(Long id){
        Optional<Sprint> sprintById = sprintRepository.findById(id);
        Sprint sprint1 = new Sprint();
        if ( sprintById.isPresent()){
            sprint1 = sprintById.get();
        }
        return sprint1;
    }
}
