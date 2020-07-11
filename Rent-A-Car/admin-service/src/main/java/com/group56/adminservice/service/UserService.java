package com.group56.adminservice.service;

import com.group56.adminservice.DTO.UserDTO;
import com.group56.adminservice.client.SoapClient;
import com.group56.adminservice.model.User;
import com.group56.adminservice.repository.UserRepository;
import com.group56.adminservice.utility.MessagePublisher;
import com.group56.soap.GetUsersRequest;
import com.group56.soap.GetUsersResponse;
import com.group56.soap.UserXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private SoapClient soapClient;
    private MessagePublisher messagePublisher;

    @Autowired
    public UserService(UserRepository userRepository, SoapClient soapClient, MessagePublisher messagePublisher) {
        this.userRepository = userRepository;
        this.soapClient = soapClient;
        this.messagePublisher = messagePublisher;
    }

    @Transactional
    public ResponseEntity<?> blockUser(UserDTO userDTO) {
        User user = userRepository.findUserByUsername(userDTO.getUsername());
        if(user != null) {
            if(user.isActive()){
                user.setBlocked(true);
                user.setModified(true);
                userRepository.save(user);
                messagePublisher.sendAMessageToQueue("USER_MODIFIED");

                return new ResponseEntity<>("User is blocked!", HttpStatus.OK);
            }
            return new ResponseEntity<>("User is already deleted!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Couldn't find user with provided username", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> activeUser(UserDTO userDTO){
        User user = userRepository.findUserByUsername(userDTO.getUsername());
        if(user != null) {
            if(user.isActive()){
                user.setBlocked(false);
                user.setModified(true);
                userRepository.save(user);
                return new ResponseEntity<>("User is activated!", HttpStatus.OK);
            }
            return new ResponseEntity<>("User deleted!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Couldn't find user with provided username", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> removeUser(UserDTO userDTO) {
        User user = userRepository.findUserByUsername(userDTO.getUsername());
        if(user != null) {
            if(user.isActive()){
                user.setActive(false);
                user.setModified(true);
                userRepository.save(user);
                return new ResponseEntity<>("User is successfully deleted!", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>("User is already deleted!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Couldn't find user with provided username", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getActiveUsers() {
        List<User> users = userRepository.findAll();
        users = users.stream().filter(User::isActive).collect(Collectors.toList());
        return new ResponseEntity<>(makeDTOFromUsers(users), HttpStatus.OK);
    }

    public ResponseEntity<?> getModifiedUsers() {
        List<User> users = userRepository.findAll();
        users = users.stream().filter(User::isModified).collect(Collectors.toList());

        for(User user : users) {
            user.setModified(false);
            userRepository.save(user);
        }

        return new ResponseEntity<>(makeDTOFromUsers(users), HttpStatus.OK);
    }

    public List<UserXML> getUsersXMLFromSOAPRequest() {
        GetUsersRequest request = new GetUsersRequest();
        request.setUsername("");
        GetUsersResponse response = soapClient.getUsers(request);
        return response.getUser();
    }

    private List<UserDTO> makeDTOFromUsers(List<User> users) {
        List usersDTO = new ArrayList<>();
        users.forEach(user -> {
            UserDTO userDTO = UserDTO.builder().build();
            userDTO.setUsername(user.getUsername());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setAddress(user.getAddress());
            userDTO.setEmail(user.getEmail());
            userDTO.setActive(user.isActive());
            userDTO.setBlocked(user.isBlocked());
            userDTO.setNumberOfAdvertsCancelled(user.getNumberOfAdvertsCancelled());

            usersDTO.add(userDTO);
        });

        return usersDTO;
    }

    public void saveUsersToDatabase(List<User> users) {
        users.forEach(user -> {
            userRepository.save(user);
        });
    }

    public ResponseEntity<?> test() {
        User user1 = User.builder()
                .email("user@gmail.com")
                .username("user1")
                .firstName("Petar")
                .lastName("Kovacevic")
                .address("Karadjordjeva")
                .isActive(true)
                .isBlocked(false)
                .numberOfAdvertsCancelled(0)
                .build();
        User user2 = User.builder()
                .email("user2@gmail.com")
                .username("user2")
                .firstName("Danica")
                .lastName("Vuckovic")
                .address("Dositejeva")
                .isActive(true)
                .isBlocked(false)
                .numberOfAdvertsCancelled(0)
                .build();
        User user3 = User.builder()
                .email("user3@gmail.com")
                .username("user3")
                .firstName("Ana")
                .lastName("Jovanovic")
                .address("Cara Dusana")
                .isActive(true)
                .isBlocked(false)
                .numberOfAdvertsCancelled(0)
                .build();
        User user4 = User.builder()
                .email("user4@gmail.com")
                .username("user4")
                .firstName("Sima")
                .lastName("Petrovic")
                .address("Novo Brdo")
                .isActive(true)
                .isBlocked(false)
                .numberOfAdvertsCancelled(0)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        return new ResponseEntity<>("Users added", HttpStatus.CREATED);
    }
}
