package converter;

import dto.ConferenceDTO;
import model.Conference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConferenceConverter extends BaseConverter<Conference, ConferenceDTO> {

    @Override
    public Conference convertDtoToModel(ConferenceDTO dto) {
        Conference conference = Conference.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .phase(dto.getPhase())
                .abstractDeadline(dto.getAbstractDeadline())
                .fullPapersDeadline(dto.getFullDeadline())
                .biddingDeadline(dto.getBiddingDeadline())
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
                .build();
        dto.setId(conference.getId());

        return dto;
    }
}
