package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersDTO {
    List<UserDTO> users;
    private Integer page;
    private Integer pagesCount;
}
