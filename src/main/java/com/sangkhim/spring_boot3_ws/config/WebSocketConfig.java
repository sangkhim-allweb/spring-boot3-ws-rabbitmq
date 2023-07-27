package com.sangkhim.spring_boot3_ws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private static final Boolean useInMemoryBroker = false;

  @Value("${spring.rabbitmq.host}")
  private String host;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app");
    registry.setUserDestinationPrefix("/user");

    if (useInMemoryBroker) {
      // Enables a simple in-memory broker
      registry.enableSimpleBroker("/topic", "/queue");
    } else {
      String rabbitMqHost = host;
      String rabbitMqUsername = username;
      String rabbitMqPassword = password;

      // Use this for enabling a Full featured broker like RabbitMQ
      registry
          .enableStompBrokerRelay("/topic", "/queue")
          .setRelayHost(rabbitMqHost)
          .setRelayPort(61613)
          .setClientLogin(rabbitMqUsername)
          .setClientPasscode(rabbitMqPassword)
          .setSystemLogin(rabbitMqUsername)
          .setSystemPasscode(rabbitMqPassword)
          .setUserDestinationBroadcast("/topic/log-unresolved-user")
          .setUserRegistryBroadcast("/topic/log-user-registry");
    }
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
  }
}
