package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;
import uz.nonvoyxona.app.model.state.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @OneToOne
    private Branch branch;
    @OneToOne
    private Courier courier;
}
