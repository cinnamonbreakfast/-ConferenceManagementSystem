package repository;

import model.util.Permission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PermissionRepository extends Repository<Long, Permission> {
    Permission findByUsernameAndConference(String username, Long conference);
}
