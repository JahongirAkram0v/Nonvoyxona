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
public class Baker {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @OneToMany(mappedBy = "baker")
    private List<Production> productions;

}
