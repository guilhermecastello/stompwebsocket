package br.com.guilhermecastello.stompwebsocket.controller;

import br.com.guilhermecastello.stompwebsocket.dto.MessageDTO;
import br.com.guilhermecastello.stompwebsocket.dto.ResponseMessageDTO;
import br.com.guilhermecastello.stompwebsocket.service.NotificationService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MessageController {

    private final NotificationService notificationService;

    public MessageController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ResponseMessageDTO getMessage(final MessageDTO messageDTO) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendGlobalNotification();
        return new ResponseMessageDTO(HtmlUtils.htmlEscape(messageDTO.getText()));
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public ResponseMessageDTO getPrivateMessage(final MessageDTO messageDTO, final Principal principal) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendPrivateNotification(principal.getName());
        return new ResponseMessageDTO(HtmlUtils.htmlEscape("Sending message to user " + principal.getName() + " : " + messageDTO.getText()));
    }
}
