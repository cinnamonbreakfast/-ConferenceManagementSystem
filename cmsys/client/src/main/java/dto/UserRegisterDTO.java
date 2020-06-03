package dto;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class UserRegisterDTO implements Serializable {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
