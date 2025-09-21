package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InStoreItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "in_store_id")
    private InStore inStore;

    private String name;
    private int price;
    private int quantity;
}
