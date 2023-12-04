package com.example.mhcdemo.repository;

import com.example.mhcdemo.repository.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Modifying
    @Query("update Event e " +
            "set e.status = ?1, e.approvedDate = ?2, e.remarks = ?3 " +
            "where e.id = ?4")
    void updateEventStatus(String status, Date date, String remarks, Long id);
}
