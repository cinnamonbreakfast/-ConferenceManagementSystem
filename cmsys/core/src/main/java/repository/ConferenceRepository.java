package repository;

import model.Conference;
import model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConferenceRepository extends Repository<Long, Conference> {
    List<Conference> getForUsername(String username);
    User getChair(Long id);
}
