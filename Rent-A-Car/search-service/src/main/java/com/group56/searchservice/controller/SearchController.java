package com.group56.searchservice.controller;

import com.group56.searchservice.DTO.AdvertQueryDTO;
import com.group56.searchservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/search-service")
public class SearchController {
    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<?> findRelevantAdverts(@RequestBody AdvertQueryDTO advertDTO) {
        return searchService.findRelevantAdverts(advertDTO);
    }

    @GetMapping("/{advertId}")
    public ResponseEntity<?> findSingleAdvert(@PathVariable Long advertId) {
        return searchService.findAdvertById(advertId);
    }
}
