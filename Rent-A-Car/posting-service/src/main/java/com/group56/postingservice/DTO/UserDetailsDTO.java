package com.group56.postingservice.DTO;

import lombok.*;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDetailsDTO {
    @Id
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private int numberOfAdvertsCancelled;
}
