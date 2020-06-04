package model.util;

import lombok.*;
import model.Entity;
import model.User;

import javax.persistence.*;

@Table(name = "assigned_papers", schema = "development")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@org.hibernate.annotations.NamedQuery(name = "Assigns.myAssigns", query = "SELECT p FROM Paper p INNER JOIN Assigns a ON a.paper = p INNER JOIN User u ON a.user = u INNER JOIN Permission r ON a.user = r.user WHERE u.username = ?1 AND r.conference.id = ?2")
public class Assigns extends Entity<Long> {
    @ManyToOne
    @JoinColumn(name="paper_id", nullable=false)
    Paper paper;

    @ManyToOne
    @JoinColumn(name="reviewer_rd", nullable=false)
    User user;

    @Column
    Boolean declined;
}
