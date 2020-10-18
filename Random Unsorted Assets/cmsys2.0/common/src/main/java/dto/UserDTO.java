package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class UserDTO extends BaseDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String descriptor;
}
