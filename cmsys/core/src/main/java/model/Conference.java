package model;

import lombok.*;

import javax.persistence.*;

@Table(name = "conference", schema = "development")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Conference extends Entity<Long> {

    @ManyToOne
    @JoinColumn(name="chair", nullable=false)
    private User chair;

    @Column(nullable = true)
    private Integer phase;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String description;

    // TODO: to add when Sections are ready - OneToMany relationship here
//    private List<Sections> sections;
}
