package uz.nonvoyxona.app.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionDTO {

    private int branchId;
    private int bakerId;
    private int productId;
    private int quantity;
}
