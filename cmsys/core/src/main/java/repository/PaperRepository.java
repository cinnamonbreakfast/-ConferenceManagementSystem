package repository;

import model.util.Paper;

import java.util.List;

public interface PaperRepository extends Repository<Long, Paper> {
    List<Paper> getForUser(String username);
    List<Paper> getForConference(Long Id);
    List<Paper> getByUandC(String username, Long conference);
}
