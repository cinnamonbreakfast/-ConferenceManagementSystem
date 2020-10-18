package converter;

import dto.PermissionDTO;
import model.util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.ConferenceRepository;
import repository.UserRepository;

@Component
public class PermissionConverter extends BaseConverter<Permission, PermissionDTO> {

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Permission convertDtoToModel(PermissionDTO dto) {
        Permission permission = Permission.builder()
                .author(dto.getAuthor())
                .chair(dto.getChair())
                .coChair(dto.getCoChair())
                .build();
        permission.setId(dto.getId());
        conferenceRepository.findById(dto.getConference()).ifPresent(permission::setConference);
        userRepository.findById(dto.getUser()).ifPresent(permission::setUser);
        return permission;
    }

    @Override
    public PermissionDTO convertModelToDto(Permission permission) {
        PermissionDTO dto = PermissionDTO.builder()
                .author(permission.getAuthor())
                .chair(permission.getChair())
                .coChair(permission.getCoChair())
                .reviewer(permission.getReviewer())
                .user(permission.getUser().getId())
                .conference(permission.getConference().getId())
                .build();
        dto.setId(permission.getId());

        return dto;
    }
}
