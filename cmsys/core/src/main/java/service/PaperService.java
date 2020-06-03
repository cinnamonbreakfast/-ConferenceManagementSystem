package service;

import model.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PaperRepository;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class PaperService {
    private PaperRepository paperRepository;

    @Autowired
    public PaperService(PaperRepository paperRepository)
    {
        this.paperRepository = paperRepository;
    }

    public PaperRepository getPaperRepository()
    {
        return this.paperRepository;
    }

    public Paper add(Paper paper)
    {
        return this.paperRepository.save(paper);
    }

    public Boolean delete(Long id)
    {
        AtomicBoolean deleted = new AtomicBoolean(false);
        paperRepository.findById(id).ifPresent(paper -> {
            paperRepository.delete(paper);
            deleted.set(true);
        });
        return deleted.get();
    }
}
