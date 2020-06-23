package com.group56.reviewservice.listener;

import com.group56.reviewservice.model.Advert;
import com.group56.reviewservice.model.User;
import com.group56.reviewservice.service.AdvertService;
import com.group56.reviewservice.service.UserService;
import com.group56.soap.AdvertXML;
import com.group56.soap.UserXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MessageListener {
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);
    private UserService userService;
    private AdvertService advertService;

    @Autowired
    public MessageListener(UserService userService, AdvertService advertService) {
        this.userService = userService;
        this.advertService = advertService;
    }

    public void listenForMessages(byte[] bytes) {
        String event = new String(bytes, StandardCharsets.UTF_8);
        switch (event) {
            case "USER_ADDED": handleUserData();
            //case "ADVERT_ADDED" : handleAdvertData();
            break;
            default: logger.error("Data received from auth-service is not well formatted");
        }
    }

    private void handleUserData(){
        List<UserXML> userXML = userService.getUsersXMLFromSOAPRequest();
        List<User> users = formUsersFromXML(userXML);
        userService.saveUsersToDatabase(users);
    }

    private List<User> formUsersFromXML(List<UserXML> userXML) {
        List<User> users = new ArrayList<>();

        userXML.forEach(data -> {
            User user = User.builder()
                    .username(data.getUsername())
                    .isActive(data.isIsActive())
                    .isBlocked(data.isIsBlocked())
                    .build();
            users.add(user);
        });

        return users;
    }

    private void handleAdvertData() {
        List<AdvertXML> advertsXML = advertService.getAdvertsXMLFromSoapRequest();
        List<Advert> adverts = formAdvertsFromXML(advertsXML);
        advertService.saveAdverts(adverts);
    }

    private List<Advert> formAdvertsFromXML(List<AdvertXML> advertsXML) {
        List<Advert> adverts = new ArrayList<>();
        advertsXML.forEach(data -> {
            Advert advert = Advert.builder()
                    .uuid(UUID.fromString(data.getUuid()))
                    .isActive(data.isIsActive())
                    .build();

            adverts.add(advert);
        });
        return adverts;
    }


}
