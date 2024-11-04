package org.example.websocket.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage){
        ChatMessage saveMassage = chatMessageService.save(chatMessage);
        log.info("chatMessage {} saved", saveMassage);

        ChatNotification notification = ChatNotification.builder()
                        .id(saveMassage.getId())
                        .senderId(saveMassage.getSenderId())
                        .recipientId(saveMassage.getRecipientId())
                        .content(saveMassage.getContent())
                        .build();

        log.info("Created notification {}", notification);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                notification);
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ){
        List<ChatMessage> chatMessages = chatMessageService.findChatMessage(senderId, recipientId);
        log.info("Find {} chatMessages", chatMessages);

        return ResponseEntity.ok(chatMessages);
    }
}
