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
    @JoinColumn(name = "branchProduct_id")
    private BranchProduct branchProduct;

    private int quantity;
}
