package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PaperDTO extends BaseDTO {
    String title;
    String keywords;
    String topics;
    UserDTO user;
    ConferenceDTO conferenceDTO;
}
