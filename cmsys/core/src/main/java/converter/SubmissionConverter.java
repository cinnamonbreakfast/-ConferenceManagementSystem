package converter;

import dto.SubmissionDTO;
import model.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmissionConverter extends BaseConverter<Submission , SubmissionDTO> {
    private final UserConverter userConverter;
    private final PaperConverter paperConverter;

    @Autowired
    public SubmissionConverter(UserConverter userConverter, PaperConverter paperConverter)
    {
        this.userConverter = userConverter;
        this.paperConverter = paperConverter;
    }

    @Override
    public Submission convertDtoToModel(SubmissionDTO dto) {
        Submission submission = Submission.builder().author(userConverter.convertDtoToModel(dto.getUser()))
                                                    .paper(paperConverter.convertDtoToModel(dto.getPaper())).build();
        submission.setId(dto.getId());

        return submission;
    }

    @Override
    public SubmissionDTO convertModelToDto(Submission submission) {
        SubmissionDTO submissionDTO = SubmissionDTO.builder().user(userConverter.convertModelToDto(submission.getAuthor()))
                                                            .paper(paperConverter.convertModelToDto(submission.getPaper())).build();

        submissionDTO.setId(submission.getId());

        return submissionDTO;
    }
}
