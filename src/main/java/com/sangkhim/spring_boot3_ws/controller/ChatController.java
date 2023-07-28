package com.sangkhim.spring_boot3_ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  @Autowired private SimpMessageSendingOperations messagingTemplate;

  @MessageMapping("/topic/{room}")
  public void topic(
      @DestinationVariable("room") String room,
      @Payload String chatMessage,
      SimpMessageHeaderAccessor headerAccessor) {

    // send message
    messagingTemplate.convertAndSend("/topic/" + room, chatMessage);
  }

  @MessageMapping("/user/{recipient}")
  public void user(
      @DestinationVariable("recipient") String recipient,
      @Payload String chatMessage,
      SimpMessageHeaderAccessor headerAccessor) {

    // send message
    messagingTemplate.convertAndSendToUser(recipient, "/queue/reply", chatMessage);
  }
}
