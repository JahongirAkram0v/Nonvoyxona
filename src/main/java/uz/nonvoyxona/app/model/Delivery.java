package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;
import uz.nonvoyxona.app.model.state.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static uz.nonvoyxona.app.model.state.DeliveryStatus.CREATED;

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
    private int id;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus = CREATED;

    @Builder.Default
    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "delivery_item_index")
    private List<DeliveryItem> deliveryItems = new ArrayList<>();

    private String clientName;
    private String clientPhoneNumber;
    private String clientAddress;

    private Long totalPrice;

    private LocalDateTime orderDateTime;
}
