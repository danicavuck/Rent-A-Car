package com.group56.postingservice.DTO;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RentRequestDTO {
    private String uuid;
    private String username;
    private boolean bundle;
    private String timeStart;
    private String timeEnd;
    private String dateStart;
    private String dateEnd;
    private ArrayList<String> advertUUIDs;
}
