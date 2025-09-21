package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;
import uz.nonvoyxona.app.model.state.ItemStatus;

import java.time.LocalDateTime;

import static uz.nonvoyxona.app.model.state.ItemStatus.PREPARING;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus = PREPARING;

    private String name;
    private int price;
    private int quantity;
}
