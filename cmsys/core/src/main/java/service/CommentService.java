package service;

import model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    CommentService(CommentRepository cr){
        this.commentRepository=cr;
    }

    public Comment addComment(Comment comment){
        return this.commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment){
        return this.commentRepository.save(comment);
    }
    public Comment deleteComment(Long id){
        Optional<Comment> c=commentRepository.findById(id);
        if (c.isPresent())
        {
            this.commentRepository.deleteById(id);
            return c.get();
        }
        return null;
    }
    public List<Comment>getAll(){
        return this.commentRepository.findAll();
    }

    public void upvote(Long id){
        this.commentRepository.upvote(id);
    }

}
