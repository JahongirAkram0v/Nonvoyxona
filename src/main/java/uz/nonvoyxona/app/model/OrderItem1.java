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
public class OrderItem1 {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order1 order1;

    @ManyToOne
    @JoinColumn(name = "branchProduct_id")
    private BranchProduct branchProduct;

    private int quantity;
}
