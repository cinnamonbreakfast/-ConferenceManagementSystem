package dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ConferenceDTO extends BaseDTO {
    private UserDTO chair;
    private String title;
    private String description;
    private Integer phase;
}
