package service;

import model.Conference;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ConferenceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public List<Conference> getForUsername(String username) {
        return conferenceRepository.getForUsername(username);
    }

    public Conference add(Conference conference) {
        return this.conferenceRepository.save(conference);
    }

    public User getChair(Long id) {
        return this.conferenceRepository.getChair(id);
    }

    public Conference getById(Long id) {
        Optional<Conference> resp = conferenceRepository.findById(id);
        return resp.orElse(null);
    }
}
