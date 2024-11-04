package org.example.websocket.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocket.chatRoom.ChatRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService.getChatId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow(() -> new RuntimeException("Do not get chatId from chatRoomService"));

        chatMessage.setChatId(chatId);
        repository.save(chatMessage);

        log.info("chatMessage {} saved", chatMessage);

        return chatMessage;
    }

    public List<ChatMessage> findChatMessage(String senderId, String recipientId) {
        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);

        if (chatId.isPresent()) {
            log.info("chatId {} found", chatId);
            return repository.findByChatId(chatId.get());
        }

        log.info("chatId {} not found", chatId);
        return new ArrayList<>();
    }
}
