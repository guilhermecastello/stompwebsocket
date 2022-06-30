package br.com.guilhermecastello.stompwebsocket.controller;

import br.com.guilhermecastello.stompwebsocket.dto.MessageDTO;
import br.com.guilhermecastello.stompwebsocket.service.MessageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageWebsocketController {

    private final MessageService messageService;

    public MessageWebsocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody final MessageDTO messageDTO) {
        messageService.notifyFrontend(messageDTO.getText());
    }

    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final String id, @RequestBody final MessageDTO messageDTO) {
        messageService.notifyUser(messageDTO.getText(), id);
    }
}
