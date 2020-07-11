package com.group56.searchservice.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdvertSortDTO {
    private String criteria;
    private List<AdvertPreviewDTO> adverts;
}
