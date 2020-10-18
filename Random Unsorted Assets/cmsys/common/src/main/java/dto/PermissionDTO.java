package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PermissionDTO extends BaseDTO {
    private Long conference;
    private Long user;
    private Boolean author;
    private Boolean chair;
    private Boolean coChair;
    private Boolean reviewer;
}
