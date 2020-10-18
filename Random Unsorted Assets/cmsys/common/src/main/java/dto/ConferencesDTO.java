package dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class ConferencesDTO implements Serializable {
    private List<ConferenceDTO> conferences;
    private ConferenceDTO probe;
    private Integer size;
    private Integer pages;
}
