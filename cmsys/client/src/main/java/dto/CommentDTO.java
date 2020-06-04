package dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class CommentDTO extends BaseDTO{
    private UserDTO author;
    private String text;
    private Integer upvotes;
    private LocalDateTime posttime;
}
