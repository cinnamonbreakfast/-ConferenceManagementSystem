package converter;

import dto.PaperDTO;
import model.util.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.ConferenceRepository;
import repository.UserRepository;

@Component
public class PaperConverter extends BaseConverter<Paper, PaperDTO> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ConferenceConverter conferenceConverter;
    @Autowired
    UserConverter userConverter;


    @Override
    public Paper convertDtoToModel(PaperDTO dto) {
        Paper paper = Paper.builder()
                .title(dto.getTitle())
                .keywords(dto.getKeywords())
                .topics(dto.getTopics())
                .conference(conferenceConverter.convertDtoToModel(dto.getConferenceDTO()))
                .user(userConverter.convertDtoToModel(dto.getUser()))
                .build();
        paper.setId(dto.getId());
        return paper;
    }

    @Override
    public PaperDTO convertModelToDto(Paper paper) {
        PaperDTO paperDTO = PaperDTO.builder()
                .title(paper.getTitle())
                .keywords(paper.getKeywords())
                .topics(paper.getTopics())
                .user(userConverter.convertModelToDto(paper.getUser()))
                .conferenceDTO(conferenceConverter.convertModelToDto(paper.getConference()))
                .build();
        paperDTO.setId(paper.getId());

        return paperDTO;
    }
}