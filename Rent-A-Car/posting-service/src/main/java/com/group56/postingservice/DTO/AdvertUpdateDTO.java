package com.group56.postingservice.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdvertUpdateDTO {
    private Long id;
    private LocalDateTime rentFrom;
    private LocalDateTime rentUntil;

}
