package com.example.mhcdemo.repository;

import com.example.mhcdemo.repository.entity.Account;
import com.example.mhcdemo.repository.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {


    @Query("select e.type from EventType e")
    List<String> findAllEventTypes();

    EventType findFirstByType(String eventType);
}
