package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InStore {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Builder.Default
    @OneToMany(mappedBy = "inStore", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "in_store_item_index")
    private List<InStoreItem> inStoreItems = new ArrayList<>();

    private Long totalPrice;

    private LocalDateTime orderDateTime;
}
