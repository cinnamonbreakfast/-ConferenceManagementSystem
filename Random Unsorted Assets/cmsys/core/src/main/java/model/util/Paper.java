package model.util;

import lombok.*;
import model.Conference;
import model.Entity;
import model.User;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;

@Table(name = "papers", schema = "development")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NamedQuery(name = "Paper.getForUser", query = "SELECT p FROM Paper p INNER JOIN Conference c ON p.conference = c INNER JOIN User u ON p.user = u WHERE u.username = ?1")
@NamedQuery(name = "Paper.getForConference", query = "SELECT p FROM Paper p INNER JOIN Conference c ON p.conference = c INNER JOIN User u ON p.user = u WHERE c.id = ?1")
@NamedQuery(name = "Paper.getByUandC", query = "SELECT p FROM Paper p INNER JOIN Conference c ON p.conference = c INNER JOIN User u ON p.user = u WHERE c.id = ?2 AND u.username = ?1")
public class Paper extends Entity<Long> {
    @Column
    String title;
    @Column
    String keywords;
    @Column
    String topics;

    @OneToOne
    @JoinColumn(name="user_id", nullable=false)
    User user;

    @ManyToOne
    @JoinColumn(name="conf_id", nullable=false)
    Conference conference;
}
