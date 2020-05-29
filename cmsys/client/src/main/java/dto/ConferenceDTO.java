package dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    private LocalDateTime abstractDeadline;
    private LocalDateTime fullDeadline;
    private LocalDateTime biddingDeadline;

    private List<String> cochairs;
    private List<String> authors;
    private List<String> reviewers;
}
