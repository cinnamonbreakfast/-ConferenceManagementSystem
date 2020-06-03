package converter;

import dto.PaperDTO;
import dto.UserDTO;
import model.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaperConverter extends BaseConverter<Paper, PaperDTO> {

    private final UserConverter userConverter;

    @Autowired
    public PaperConverter(UserConverter userConverter)
    {
        this.userConverter = userConverter;
    }

    @Override
    public Paper convertDtoToModel(PaperDTO dto) {

        Paper paper = Paper.builder().name(dto.getName())
                                    .abstractURL(dto.getAbstractURL()).build();
        paper.setId(dto.getId());
        return paper;
    }

    @Override
    public PaperDTO convertModelToDto(Paper paper) {
        List<UserDTO> users = userConverter.convertModelsToDtos(paper.getOtherAuthors());
        PaperDTO dto = PaperDTO.builder().name(paper.getName())
                                        .abstractURL(paper.getAbstractURL())
                                        .otherAuthors(users).build();
        dto.setId(paper.getId());
        return dto;
    }
}
