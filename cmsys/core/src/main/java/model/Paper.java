package model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Table(name = "papers", schema = "development")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Paper extends Entity<Long>{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String abstractURL;

    /*private List<String> keywords;
    private List<String> topic;*/
    @ManyToOne
    @JoinColumn(name = "users", nullable = true)
    private List<User> otherAuthors;
}
