package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nonvoyxona.app.model.User;
import uz.nonvoyxona.app.model.dto.UserDTO;
import uz.nonvoyxona.app.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/connection")
@RequiredArgsConstructor
public class ConnectionController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody UserDTO userDTO) {

        Optional<User> optionalUser = userService.findByUsername(userDTO.getUsername());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        if (user.getUserRole() != userDTO.getUserRole()) {
            return ResponseEntity.badRequest().body("FAILURE: Incorrect role.");
        }
        if (!user.getPassword().equals(userDTO.getPassword())) {
            return ResponseEntity.badRequest().body("FAILURE: Incorrect password.");
        }

        return ResponseEntity.ok(user.getId());
    }
}
