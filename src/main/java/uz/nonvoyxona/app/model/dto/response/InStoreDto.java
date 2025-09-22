package uz.nonvoyxona.app.model.dto.response;

import lombok.Setter;

import java.util.List;

@Setter
public class InStoreDto {

    private int id;
    private List<ItemDto> inStoreItems;
    private Long totalPrice;
}
