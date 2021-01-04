package com.advertisementproject.zuulgateway.db.repositories;

import com.advertisementproject.zuulgateway.db.models.NewsLetter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsLetterRepository extends JpaRepository<NewsLetter, UUID> {
}
