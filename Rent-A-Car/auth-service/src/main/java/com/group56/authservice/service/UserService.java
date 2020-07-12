package com.group56.authservice.service;

import com.group56.authservice.DTO.LoginDTO;
import com.group56.authservice.DTO.UserDTO;
import com.group56.authservice.enumeration.Role;
import com.group56.authservice.model.Authorization;
import com.group56.authservice.model.User;
import com.group56.authservice.repository.UserRepository;
import com.group56.authservice.utility.IdentityCheck;
import com.group56.authservice.utility.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private IdentityCheck identityCheck;
    private MessagePublisher messagePublisher;

    @Autowired
    public UserService(UserRepository userRepository, IdentityCheck identityCheck, MessagePublisher publisher) {
        this.userRepository = userRepository;
        this.identityCheck = identityCheck;
        this.messagePublisher = publisher;
    }

    @Transactional
    public ResponseEntity<?> registerNewUser(UserDTO userDTO){
        if(identityCheck.isUsernameUnique(userDTO.getUsername())){
            if(identityCheck.isEmailAddressUnique(userDTO.getEmail())) {
                User user = createUserFromDTO(userDTO);
                userRepository.save(user);

                messagePublisher.sendAMessageToQueue("USER_ADDED");
                return new ResponseEntity<>("Account successfully created", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Email address is already in use", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Username is not unique", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> logInUser(LoginDTO loginDTO, HttpSession session) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if(user.getPassword().equals(loginDTO.getPassword())) {
            if(!user.isActive()) {
                return new ResponseEntity<>("Your account has been suspended by admin", HttpStatus.FORBIDDEN);
            }
            session.setAttribute("ROLE", Role.USER);
            session.setAttribute("USERNAME", user.getUsername());
            return new ResponseEntity<>("USER", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
    }

    private User createUserFromDTO(UserDTO userDTO) {
        return User.builder().username(userDTO.getUsername()).password(userDTO.getPassword())
                .firstName(userDTO.getFirstName()).lastName(userDTO.getLastName())
                .isActive(true).isBlocked(false).isSharedWithAdmin(false).isBlocked(false)
                .address(userDTO.getAddress())
                .email(userDTO.getEmail()).build();
    }

    public List<User> getUsers(String username) {
        List<User> users = new ArrayList();
        User user1 = User.builder().email("user@gmail.com")
                .username("Skinny Pete").firstName("Petar")
                .lastName("Kovacevic").address("Karadjordjeva 14")
                .isActive(true).isBlocked(false).build();
        User user2 = User.builder().email("zoran@gmail.com")
                .username("Zoki").firstName("Zoran")
                .lastName("Simic").address("Dositejeva 15")
                .isActive(true).isBlocked(false).build();

        User user3 = User.builder().email("laki@gmail.com")
                .username("Zola").firstName("Lazar")
                .lastName("Grozdanovic").address("Omladinska")
                .isActive(true).isBlocked(false).build();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }

    public List<User> getUsersThatAreNotSharedWithAdminService(String username) {
        List<User> allUsers = userRepository.findAll();
        List<User> notShared = new ArrayList<>();

        allUsers.forEach(user -> {
            if(!user.isSharedWithAdmin()) {
                notShared.add(user);
                user.setSharedWithAdmin(true);
                userRepository.save(user);
            }

        });
        return notShared;
    }

    public void fetchUsers() {
        RestTemplate restTemplate = new RestTemplate();
        String apiEndpoint = "http://localhost:8080/admin-service/user/modified";
        ResponseEntity<User[]> response = restTemplate.getForEntity(apiEndpoint, User[].class);
        User[] users = response.getBody();

        for(int i = 0; i<users.length; i++) {
            modifyAndSaveUser(users[i]);
        }
    }
    private void modifyAndSaveUser(User modifiedUser) {
        User user = userRepository.findByUsername(modifiedUser.getUsername());
        if(user != null) {
            user.setActive(modifiedUser.isActive());
            user.setBlocked(modifiedUser.isBlocked());
            userRepository.save(user);
        }
    }

}
