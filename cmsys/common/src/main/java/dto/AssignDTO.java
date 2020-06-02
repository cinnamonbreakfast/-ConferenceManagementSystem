package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class AssignDTO extends BaseDTO {
    UserDTO reviewer;
    PaperDTO paper;
    Boolean declined;
    String review;
    String rating;
}
