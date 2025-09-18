package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    private int quantity = 0;

    @Builder.Default
    @OneToMany(mappedBy = "branchProduct", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "baker_index")
    private List<OrderItem> orderItems =  new ArrayList<>();
}
