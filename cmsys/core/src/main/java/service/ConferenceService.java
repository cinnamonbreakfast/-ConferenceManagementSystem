package service;

import model.Conference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ConferenceRepository;

@Service
public class ConferenceService {

    ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public Conference add(Conference conference) {
        return this.conferenceRepository.save(conference);
    }
}
