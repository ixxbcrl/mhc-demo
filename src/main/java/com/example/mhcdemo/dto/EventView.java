package com.example.mhcdemo.dto;

import com.example.mhcdemo.repository.entity.Company;
import com.example.mhcdemo.repository.entity.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventView {

    private Long eventId;
    private String companyName;
    private LocalDate date1;
    private LocalDate date2;
    private LocalDate date3;
    private LocalDate confirmedDate;
    private String proposalLocation;
    private String eventType;
    private List<LocalDate> dates;
    private String eventStatus;
    private List<String> eventTypeList;
    private LocalDate dateCreated;
    private String createdBy;
    private String remarks;
    private List<Company> vendorCompanies;
}
