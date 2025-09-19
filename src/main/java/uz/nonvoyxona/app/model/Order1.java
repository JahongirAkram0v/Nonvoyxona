package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static uz.nonvoyxona.app.model.OrderState1.NOT_READY;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "_order")
public class Order1 {

    @Id
    @GeneratedValue
    private int id;
    @Builder.Default
    private OrderState1 orderState1 = NOT_READY;
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch order;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;
    @Builder.Default
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem1> items = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
}
