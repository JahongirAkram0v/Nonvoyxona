package uz.nonvoyxona.app.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierDTO extends UserDTO {

    private String name;
    private String phoneNumber;
}
