package model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import org.hibernate.annotations.NamedQuery;

@Table(name="comments", schema="development")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NamedQuery(name="Comment.upvote", query= "update Comment c SET c.upvotes=c.upvotes + 1 WHERE c.id=?1")
public class Comment extends Entity<Long>{
    @Basic(optional = false)
    @Column(nullable = false)
    private String text;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer upvotes;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime posttime;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="author",nullable=false)
    private User author;

}
