package converter;

import dto.UserDTO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends BaseConverter<User, UserDTO> {

    @Override
    public User convertDtoToModel(UserDTO dto) {
        User client = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public UserDTO convertModelToDto(User user) {
        UserDTO dto = UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
        dto.setId(user.getId());

        return dto;
    }
}