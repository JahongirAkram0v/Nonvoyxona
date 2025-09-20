package uz.nonvoyxona.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Production {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "baker_id")
    private Baker baker;

    private String name;
    private int price;
    private int quantity;

    //togirlashim kerakdir
    private LocalDateTime productionDateTime;
}
