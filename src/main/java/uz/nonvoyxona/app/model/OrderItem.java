package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
public class OrderItem {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}
