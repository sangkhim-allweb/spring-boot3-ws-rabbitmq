package com.sangkhim.spring_boot3_ws.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {

  private static final String USERNAME_HEADER = "username";
  private static final String PASSWORD_HEADER = "password";

  private final WebSocketAuthenticatorService webSocketAuthenticatorService;

  @Override
  public Message<?> preSend(final Message<?> message, final MessageChannel channel)
      throws AuthenticationException {

    final StompHeaderAccessor accessor =
        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

    if (StompCommand.CONNECT == accessor.getCommand()) {
      final String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
      final String password = accessor.getFirstNativeHeader(PASSWORD_HEADER);

      final UsernamePasswordAuthenticationToken user =
          webSocketAuthenticatorService.getAuthenticatedOrFail(username, password);

      accessor.setUser(user);
    }

    return message;
  }
}
