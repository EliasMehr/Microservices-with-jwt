package com.advertisementproject.zuulgateway.db.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class NewsLetter {

    @Id
    private UUID id;
    private String title;
    private String content;
}
