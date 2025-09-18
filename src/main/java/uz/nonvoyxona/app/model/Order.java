package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Builder.Default
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
}
