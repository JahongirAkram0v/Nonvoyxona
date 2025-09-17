package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
public class BranchProduct {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Product product;

    private int quantity;

    @OneToMany(mappedBy = "branchProduct")
    private List<OrderItem> orderItems;
}
