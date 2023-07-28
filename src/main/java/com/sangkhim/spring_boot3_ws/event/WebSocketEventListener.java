package com.sangkhim.spring_boot3_ws.event;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
@Slf4j
public class WebSocketEventListener {

  @EventListener
  public void handleConnectEvent(SessionConnectEvent event) {
    log.info(
        ">>> handleConnectEvent: username=" + Objects.requireNonNull(event.getUser()).getName());
  }

  @EventListener
  public void handleConnectedEvent(SessionConnectedEvent event) {
    log.info(
        ">>> handleConnectedEvent: username=" + Objects.requireNonNull(event.getUser()).getName());
  }

  @EventListener
  public void handleSubscribeEvent(SessionSubscribeEvent event) {
    log.info(
        ">>> handleSubscribeEvent: username=" + Objects.requireNonNull(event.getUser()).getName());
  }

  @EventListener
  public void handleUnsubscribeEvent(SessionUnsubscribeEvent event) {
    log.info(
        ">>> handleUnsubscribeEvent: username="
            + Objects.requireNonNull(event.getUser()).getName());
  }

  @EventListener
  public void handleDisconnectEvent(SessionDisconnectEvent event) {
    log.info(
        ">>> handleDisconnectEvent: username=" + Objects.requireNonNull(event.getUser()).getName());
  }
}
