package service;

import model.util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PermissionRepository;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission getPermission(String username, Long conference)
    {
        Permission permission = permissionRepository.findByUsernameAndConference(username, conference);

        permission.getConference().setChair(null);

        return permission;
    }
}
