package converter;

import dto.ConferenceDTO;
import model.Conference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConferenceConverter extends BaseConverter<Conference, ConferenceDTO> {


    private final UserConverter userConverter;

    @Autowired
    public ConferenceConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public Conference convertDtoToModel(ConferenceDTO dto) {
        Conference conference = Conference.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .phase(dto.getPhase())
                .build();
        conference.setId(dto.getId());
        return conference;
    }

    @Override
    public ConferenceDTO convertModelToDto(Conference conference) {
        ConferenceDTO dto = ConferenceDTO.builder()
                .title(conference.getTitle())
                .description(conference.getDescription())
                .phase(conference.getPhase())
                .chair(userConverter.convertModelToDto(conference.getChair()))
                .build();
        dto.setId(conference.getId());

        return dto;
    }
}
