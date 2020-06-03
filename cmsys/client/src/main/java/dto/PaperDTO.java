package dto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PaperDTO extends BaseDTO{
    private String name;
    private String abstractURL;
    /*private List<String> keywords;
    private List<String> topic;*/
    private List<UserDTO> otherAuthors;
}
