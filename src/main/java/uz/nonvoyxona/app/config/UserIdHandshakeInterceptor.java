package uz.nonvoyxona.app.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;
import uz.nonvoyxona.app.model.User;
import uz.nonvoyxona.app.service.UserService;

import java.util.Map;

public class UserIdHandshakeInterceptor implements HandshakeInterceptor {

    private final UserService userService;

    public UserIdHandshakeInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String userId = UriComponentsBuilder.fromUri(request.getURI()).build().getQueryParams().getFirst("userId");

        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }

        return userService.findById(userId).isPresent();
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
