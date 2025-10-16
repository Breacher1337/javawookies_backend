package com.javacake.kiosk.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final OrderSocketHandler orderSocketHandler;

    public WebSocketConfig(OrderSocketHandler orderSocketHandler) {
        this.orderSocketHandler = orderSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(orderSocketHandler, "/ws/orders")
                .setAllowedOrigins("http://localhost:3000", "http://localhost:5173");
    }
}
