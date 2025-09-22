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
@EqualsAndHashCode(of = "id")
public class BranchProduct {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Product product;

    @Builder.Default
    private int quantity = 0;
}
