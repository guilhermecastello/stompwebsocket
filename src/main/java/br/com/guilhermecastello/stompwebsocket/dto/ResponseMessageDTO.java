package br.com.guilhermecastello.stompwebsocket.dto;

public class ResponseMessageDTO {

    private String text;

    public ResponseMessageDTO(String text) {
        this.text = text;
    }

    public ResponseMessageDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
