package repository;

import model.util.Assigns;
import model.util.Paper;

import java.util.List;

public interface AssignmentRepository extends Repository<Long, Assigns> {
    List<Paper> myAssigns(String username, Long id);
}
