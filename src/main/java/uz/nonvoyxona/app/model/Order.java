package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static uz.nonvoyxona.app.model.OrderState.NOT_READY;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "_order")
public class Order {

    @Id
    @GeneratedValue
    private int id;
    @Builder.Default
    private OrderState orderState = NOT_READY;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}
