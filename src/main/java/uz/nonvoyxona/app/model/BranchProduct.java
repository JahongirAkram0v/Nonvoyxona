package uz.nonvoyxona.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

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
}
