package repository;

import model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends Repository<Long, User> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
}
