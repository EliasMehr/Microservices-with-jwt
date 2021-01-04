package com.advertisementproject.zuulgateway.db.models;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class NewsLetter {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String content;
}
