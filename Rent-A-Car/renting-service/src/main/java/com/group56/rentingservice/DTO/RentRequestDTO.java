package com.group56.rentingservice.DTO;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RentRequestDTO {
    private ArrayList<String> advertIds;
    boolean bundle;
    private String username;
}
