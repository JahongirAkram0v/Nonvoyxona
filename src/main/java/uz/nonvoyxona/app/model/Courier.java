package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    @Builder.Default
    @OneToMany(mappedBy = "courier", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}
