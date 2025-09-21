package uz.nonvoyxona.app.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeliveryDTO {

    private int branchId;
    private String clientName;
    private String clientPhoneNumber;
    private String getClientAddress;

    private List<ItemDTO> itemDTOList;
    private Long totalPrice;
}
