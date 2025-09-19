package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;
import uz.nonvoyxona.app.model.state.CourierState;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Courier {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    private String phoneNumber;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CourierState courierState = CourierState.OFFLINE;
}
