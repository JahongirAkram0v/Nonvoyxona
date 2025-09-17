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
public class Branch {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "branch")
    private List<BranchProduct> branchProducts;

    @OneToMany(mappedBy = "branch")
    private List<Baker> bakers;


}
