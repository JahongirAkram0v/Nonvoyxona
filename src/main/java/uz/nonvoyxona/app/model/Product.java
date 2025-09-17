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
public class Product {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int price;

    @OneToMany(mappedBy = "product")
    private List<BranchProduct> branchProducts;
    @OneToMany(mappedBy = "product")
    private List<Production> productions;
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

}
