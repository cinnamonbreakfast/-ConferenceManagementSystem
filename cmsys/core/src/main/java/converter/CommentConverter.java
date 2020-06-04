package converter;

import dto.CommentDTO;
import model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter extends BaseConverter<Comment, CommentDTO>{

    @Autowired
    UserConverter userConverter;

    @Override
    public Comment convertDtoToModel(CommentDTO dto){
        Comment comment=Comment.builder()
                .author(userConverter.convertDtoToModel(dto.getAuthor()))
                .text(dto.getText())
                .posttime(dto.getPosttime())
                .upvotes(dto.getUpvotes())
                .build();
        comment.setId(dto.getId());
        return comment;
    }

    @Override
    public CommentDTO convertModelToDto(Comment comment) {
        CommentDTO dto=CommentDTO.builder()
                .author(userConverter.convertModelToDto(comment.getAuthor()))
                .text(comment.getText())
                .posttime(comment.getPosttime())
                .upvotes(comment.getUpvotes())
                .build();
        dto.setId(comment.getId());
        return dto;
    }
}
