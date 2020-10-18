package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class LoginCredentials extends BaseDTO {
    private String username;
    private String password;
    private String loginTime;
}
