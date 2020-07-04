package com.group56.messagingservice.service;

import com.group56.messagingservice.dto.MessageDTO;
import com.group56.messagingservice.model.Message;
import com.group56.messagingservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ResponseEntity<?> postANewMessage(MessageDTO messageDTO) {
        Message message = formModelFromDTO(messageDTO);
        messageRepository.save(message);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    private Message formModelFromDTO(MessageDTO messageDTO) {
        return Message.builder()
                .senderUsername(messageDTO.getSenderUsername())
                .receiverUsername(messageDTO.getReceiverUsername())
                .text(messageDTO.getText())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public ResponseEntity<?> getMessageForSpecificChat(String username1, String username2) {
        List<Message> allMessages = messageRepository.findAll();
        List<Message> relevantMessages = allMessages.stream()
                .filter(msg -> msg.getSenderUsername().equals(username1) && msg.getReceiverUsername().equals(username2) ||
                        msg.getSenderUsername().equals(username2) && msg.getReceiverUsername().equals(username1))
                .sorted(Comparator.comparing(Message::getTimestamp))
                .collect(Collectors.toList());

        List<MessageDTO> messageDTOS = formDTOFromModel(relevantMessages);

        return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
    }

    private List<MessageDTO> formDTOFromModel(List<Message> messages) {
        List<MessageDTO> messageDTOs = new ArrayList<>();

        for (Message message: messages) {
            MessageDTO dto = MessageDTO.builder()
                    .senderUsername(message.getSenderUsername())
                    .receiverUsername(message.getReceiverUsername())
                    .text(message.getText())
                    .timestamp(message.getTimestamp())
                    .build();

            messageDTOs.add(dto);
        }

        return messageDTOs;
    }

    public ResponseEntity<?> getListOfUsersForSpecificUser(String username) {
        List<String> listOfUsers = new ArrayList<>();
        List<Message> allMessages = messageRepository.findAll();

        for(Message message : allMessages) {
            if(message.getSenderUsername().equals(username)) {
                listOfUsers.add(message.getReceiverUsername());
            }
            if (message.getReceiverUsername().equals(username)) {
                listOfUsers.add(message.getSenderUsername());
            }
        }

        return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
    }
}
