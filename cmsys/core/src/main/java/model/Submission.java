package model;

import lombok.*;

import javax.persistence.*;

@Table(name = "submissions", schema = "development")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Submission extends Entity<Long>{
    @ManyToOne
    @JoinColumn(name = "users", nullable = false)
    private User author;

    @OneToOne
    @JoinColumn(name = "papers", nullable = false)
    private Paper paper;

    //TODO: add section when it's done
    /*
    @OneToOne
    @JoinColumn(name = "sections", nullable = false
    private Section section;
     */
}
