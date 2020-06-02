package service;

import model.util.Assigns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AssignmentRepository;

@Service
public class AssignService {

    AssignmentRepository assignmentRepository;

    @Autowired
    public AssignService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }


    public Assigns save(Assigns assigns){
        return assignmentRepository.save(assigns);
    }

    public Assigns findById(Long id){
        return assignmentRepository.findById(id).orElse(null);
    }

    public Assigns update(Assigns ass){
        return assignmentRepository.save(ass);
    }

}

