package service;

import model.util.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AssignmentRepository;
import repository.PaperRepository;

import java.util.List;

@Service
public class PaperService {
    PaperRepository paperRepository;
    AssignmentRepository assignmentRepository;

    @Autowired
    public PaperService(PaperRepository paperRepository, AssignmentRepository assignmentRepository) {
        this.paperRepository = paperRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public List<Paper> getForUser(String user) {
        return paperRepository.getForUser(user);
    }

    public List<Paper> getForConference(Long id) {
        return paperRepository.getForConference(id);
    }

    public List<Paper> getAll() {
        return paperRepository.findAll();
    }

    public Paper save(Paper paper)
    {
        return paperRepository.save(paper);
    }

    public Paper getById(Long id)
    {
        return paperRepository.findById(id).orElse(null);
    }

    public List<Paper> getByUandC(String username, Long conference) {
        return paperRepository.getByUandC(username, conference);
    }

    public List<Paper> rAssigned(String username, Long conference) {
        return assignmentRepository.myAssigns(username, conference);
    }
}
