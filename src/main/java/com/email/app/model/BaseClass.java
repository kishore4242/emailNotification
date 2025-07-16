package com.email.app.model;


import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
public abstract class BaseClass {
    @CreatedDate
    private LocalDateTime createdAt;
}
