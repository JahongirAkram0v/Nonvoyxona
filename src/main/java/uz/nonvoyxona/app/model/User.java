package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;
import uz.nonvoyxona.app.model.state.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @OneToOne
    private Branch branch;
    @OneToOne
    private Courier courier;
}
