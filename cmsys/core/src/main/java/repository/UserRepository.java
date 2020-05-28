package repository;

import model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends Repository<Long, User> {
    User findByUsernameAndPassword(String username, String password);

    User findByEmail(String email);
    User findByUsername(String username);
}
