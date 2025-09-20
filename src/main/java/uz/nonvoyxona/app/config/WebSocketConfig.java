package uz.nonvoyxona.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import uz.nonvoyxona.app.service.UserService;

import static uz.nonvoyxona.app.model.state.UserRole.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final UserService userService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws/admin")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new UserIdHandshakeInterceptor(userService, ADMIN));

        registry.addEndpoint("/ws/branch")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new UserIdHandshakeInterceptor(userService, BRANCH));

        registry.addEndpoint("/ws/courier")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new UserIdHandshakeInterceptor(userService, COURIER));
    }
}
