package com.example.mhcdemo.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_type")
public class EventType {
    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "type")
    public String type;
}
