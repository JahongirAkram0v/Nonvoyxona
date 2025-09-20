package uz.nonvoyxona.app.model.dto;

import lombok.Getter;
import lombok.Setter;
import uz.nonvoyxona.app.model.state.UserRole;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String password;
}
