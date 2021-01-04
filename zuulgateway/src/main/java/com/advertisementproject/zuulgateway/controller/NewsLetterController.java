package com.advertisementproject.zuulgateway.controller;

import com.advertisementproject.zuulgateway.db.models.NewsLetter;
import com.advertisementproject.zuulgateway.db.repositories.NewsLetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsLetterController {

    private final NewsLetterRepository repository;

    @GetMapping
    public List<NewsLetter> getAllNews() {
        return repository.findAll();
    }

    @PostMapping
    public NewsLetter save(@RequestBody NewsLetter newsLetter) {
        repository.save(newsLetter);
        return newsLetter;
    }
}
