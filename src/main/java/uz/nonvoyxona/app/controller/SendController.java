package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendController {

    private final SimpMessagingTemplate messagingTemplate;

    public <T> void send(T payload, String endpoint) {
        messagingTemplate.convertAndSend(
                "/topic/" + endpoint,
                payload
        );
    }
}
