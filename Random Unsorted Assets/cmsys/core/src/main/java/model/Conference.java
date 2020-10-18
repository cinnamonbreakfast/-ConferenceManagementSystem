package model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "conference", schema = "development")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NamedQuery(name = "Conference.getForUsername", query = "select c from Conference c INNER JOIN Permission p on p.conference = c INNER JOIN User u ON p.user = u WHERE u.username = ?1")
@NamedQuery(name = "Conference.getChair", query = "select u from User u INNER JOIN Permission p ON p.user = u INNER JOIN Conference c ON p.conference = c WHERE c.Id = ?1 AND p.chair = true")
public class Conference extends Entity<Long> {

    @Column(nullable = true)
    private Integer phase;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(name = "abstracts")
    private LocalDateTime abstractDeadline;

    @Column(name= "fullpapers")
    private LocalDateTime fullPapersDeadline;

    @Column(name = "bidding")
    private LocalDateTime biddingDeadline;
}
