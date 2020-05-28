package model;

import lombok.*;
import model.util.Permission;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.util.List;

@Table(name="users", schema="development")
@javax.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NamedQuery(name = "User.findByUsernameAndPassword", query = "select u from User u where u.username = ?1 and u.password = ?2")
@NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email = ?1")
@NamedQuery(name = "User.findByUsername", query = "select u from User u where u.username = ?1")
public class User extends Entity<Long> {
    @Basic(optional = false)
    @Column(nullable = false)
    private String username;

    @Basic(optional = true)
    @Column(nullable = false)
    private String firstName;

    @Basic(optional = true)
    @Column(nullable = false)
    private String lastName;

    @Basic(optional = false)
    @Column(nullable = false)
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

//    @OneToMany(
//            mappedBy = "users",
//            cascade = CascadeType.PERSIST,
//            fetch = FetchType.LAZY
//    )
    @OneToMany(mappedBy = "chair", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<Conference> conferences;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, targetEntity = Permission.class)
    List<Permission> permission;
}
