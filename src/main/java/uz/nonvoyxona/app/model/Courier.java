package uz.nonvoyxona.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

import static uz.nonvoyxona.app.model.CourierState.OFFLINE;

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
    private int id;
    private String name;
    private String phoneNumber;
    @Builder.Default
    private CourierState courierState = OFFLINE;

    @OneToMany(mappedBy = "courier")
    private List<Order> orders;
}
