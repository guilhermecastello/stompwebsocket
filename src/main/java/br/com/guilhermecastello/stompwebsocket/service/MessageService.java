package br.com.guilhermecastello.stompwebsocket.service;

import br.com.guilhermecastello.stompwebsocket.dto.ResponseMessageDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;

    public MessageService(SimpMessagingTemplate simpMessagingTemplate, NotificationService notificationService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
    }

    public void notifyFrontend(final String message) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO(message);
        notificationService.sendGlobalNotification();
        simpMessagingTemplate.convertAndSend("/topic/messages", responseMessageDTO);
    }

    public void notifyUser(final String message, final String id) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO(message);
        notificationService.sendPrivateNotification(id);
        simpMessagingTemplate.convertAndSendToUser(id, "/topic/private-messages", responseMessageDTO);
    }
}
