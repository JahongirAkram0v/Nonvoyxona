package uz.nonvoyxona.app.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private int branchId;
    private List<OrderDTO> orderDTOS;
    private int totalPrice;
}
