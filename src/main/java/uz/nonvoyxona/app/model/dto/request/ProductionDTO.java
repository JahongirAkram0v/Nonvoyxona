package uz.nonvoyxona.app.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionDTO {

    private int branchId;
    private Long bakerId;
    private Integer productId;
    private Integer quantity;
}
