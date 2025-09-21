package uz.nonvoyxona.app.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InStoreDTO {

    private int branchId;
    private List<ItemDTO> itemDTOList;
    private Long totalPrice;
}
