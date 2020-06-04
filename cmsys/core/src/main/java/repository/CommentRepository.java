package repository;

import model.Comment;
import org.springframework.data.repository.NoRepositoryBean;

public interface CommentRepository extends Repository<Long, Comment>{
    public void upvote(Long id);
}
