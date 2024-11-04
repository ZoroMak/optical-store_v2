package org.example.websocket.chatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository repository;

    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
        return repository.findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                        if (createIfNotExist){
                            var chatId = this.createChatRoom(senderId, recipientId);
                            return Optional.of(chatId);
                        }

                        log.error("Chat between {} and {} not found", senderId, recipientId);
                        return Optional.empty();
                });
        }

    private String createChatRoom(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId);

        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        log.info("Chat senderRecipient created: {}", senderRecipient);

        ChatRoom recipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        log.info("Chat recipientSender created: {}", recipientSender);

        repository.save(senderRecipient);
        log.info("Chat senderRecipient saved: {}", senderRecipient);
        repository.save(recipientSender);
        log.info("Chat recipientSender saved: {}", recipientSender);

        return chatId;
    }
}
