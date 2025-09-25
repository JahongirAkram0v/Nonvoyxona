package uz.nonvoyxona.app.model.dto.response;

import lombok.Setter;

@Setter
public class DeliveryDto extends InStoreDto{

    private String courierName;
    private String clientName;
    private String clientPhoneNumber;
    private String clientAddress;
}
