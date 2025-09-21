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
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Builder.Default
    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "delivery_item_index")
    private List<DeliveryItem> deliveryItems = new ArrayList<>();

    private Long totalPrice;
    private String clientAddress;
    private String clientPhoneNumber;
    private String clientName;

    private LocalDateTime orderDateTime;
}
