package service;

import model.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SubmissionRepository;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SubmissionService {
    private SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository)
    {
        this.submissionRepository = submissionRepository;
    }

    public SubmissionRepository getSubmissionRepository()
    {
        return this.submissionRepository;
    }

    public Submission add(Submission submission)
    {
        return this.submissionRepository.save(submission);
    }

    public Boolean delete(Long id)
    {
        AtomicBoolean deleted = new AtomicBoolean(false);
        this.submissionRepository.findById(id).ifPresent(submission -> {
            this.submissionRepository.delete(submission);
            deleted.set(true);
        });
        return deleted.get();
    }
}
