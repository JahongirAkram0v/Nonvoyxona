package uz.nonvoyxona.app.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDTO extends UserDTO{

    private String name;
    private String address;
}
