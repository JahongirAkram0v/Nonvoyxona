package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DeliveryItem {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private String name;
    private int price;
    private int quantity;
}
