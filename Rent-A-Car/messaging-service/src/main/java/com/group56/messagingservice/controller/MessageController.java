package com.group56.messagingservice.controller;

import com.group56.messagingservice.dto.MessageDTO;
import com.group56.messagingservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/messaging-service")
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<?> postANewMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.postANewMessage(messageDTO);
    }

    @GetMapping("/chat/{username}")
    public ResponseEntity<?> getListOfUsersForSpecificUser(@PathVariable("username") String username) {
        return messageService.getListOfUsersForSpecificUser(username);
    }

    @GetMapping("/chat/{username1}/{username2}")
    public ResponseEntity<?> getMessageForSpecificChat(@PathVariable("username1") String username1,
                                                       @PathVariable("username2") String username2) {
        return messageService.getMessageForSpecificChat(username1, username2);
    }

}
