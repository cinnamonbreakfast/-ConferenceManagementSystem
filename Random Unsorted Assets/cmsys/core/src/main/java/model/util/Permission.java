package model.util;

import lombok.*;
import model.Conference;
import model.Entity;
import model.User;

import javax.persistence.*;

//@org.hibernate.annotations.NamedQuery(name = "Permission.findByUsernameAndConference", query = "SELECT p FROM Permission p INNER JOIN Users u ON p.user_id = u.id INNER JOIN Conference c ON p.conf_id = c.id WHERE u.username = ?1 AND c.id = ?2")

@Table(name = "permissions", schema = "development")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@org.hibernate.annotations.NamedQuery(name = "Permission.findByUsernameAndConference", query = "SELECT p FROM Permission p where p.user.username = ?1 and p.conference.id = ?2")
public class Permission extends Entity<Long> {
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="conf_id", nullable=false)
    private Conference conference;

    @Column
    private Boolean chair;

    @Column(name="co_chair")
    private Boolean coChair;

    @Column
    private Boolean reviewer;

    @Column
    private Boolean author;
}
