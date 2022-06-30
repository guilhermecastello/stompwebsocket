package br.com.guilhermecastello.stompwebsocket.service;

import br.com.guilhermecastello.stompwebsocket.dto.ResponseMessageDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendGlobalNotification(){
        ResponseMessageDTO globalNotification = new ResponseMessageDTO("Global notification");
        simpMessagingTemplate.convertAndSend("/topic/global-notification", globalNotification);
    }

    public void sendPrivateNotification(String userID){
        ResponseMessageDTO privateNotification = new ResponseMessageDTO("Private notification");
        simpMessagingTemplate.convertAndSendToUser(userID,"topic/private-notification", privateNotification);
    }
}
