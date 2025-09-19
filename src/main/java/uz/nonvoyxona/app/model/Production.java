package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Production {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "baker_id")
    private Baker baker;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    //togirlashim kerak
    @CreatedDate
    private LocalDateTime productionDateTime;
}
