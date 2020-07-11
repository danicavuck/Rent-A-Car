package com.group56.rentingservice.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RentRequestDetailsDTO {
    private String uuid;
    private String username;
    private boolean bundle;
    private String timeStart;
    private String timeEnd;
    private String dateStart;
    private String dateEnd;
    private ArrayList<String> advertUUIDs;
}
