package dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class SubmissionDTO extends BaseDTO{
    private UserDTO user;
    private PaperDTO paper;

    //TODO: add section when it's done
    //private SectionDTO section;
}
