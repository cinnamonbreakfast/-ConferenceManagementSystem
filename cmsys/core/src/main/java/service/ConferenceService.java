package service;

import model.Conference;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ConferenceRepository;

import java.util.List;

@Service
public class ConferenceService {

    ConferenceRepository conferenceRepository;

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
}
