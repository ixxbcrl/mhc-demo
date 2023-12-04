package com.example.mhcdemo.application.command;

import com.example.mhcdemo.application.query.CompanyQueryService;
import com.example.mhcdemo.application.query.EventTypeQueryService;
import com.example.mhcdemo.dto.EventView;
import com.example.mhcdemo.enums.EventStatusTypes;
import com.example.mhcdemo.repository.EventRepository;
import com.example.mhcdemo.repository.entity.Company;
import com.example.mhcdemo.repository.entity.Event;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class EventCommandService {

    EventRepository eventRepository;
    CompanyQueryService companyQueryService;
    EventTypeQueryService eventTypeQueryService;

    public EventCommandService(EventRepository eventRepository, CompanyQueryService companyQueryService,
                               EventTypeQueryService eventTypeQueryService) {
        this.eventRepository = eventRepository;
        this.companyQueryService = companyQueryService;
        this.eventTypeQueryService = eventTypeQueryService;
    }

    public void approveOrRejectEvent(String status, Date date, String eventRemarks, Long id) {
        eventRepository.updateEventStatus(status, date, eventRemarks, id);
    }

    public void createNewEvent(EventView eventView, String companyNameNew, String loggedInUserCompany, String eventTypeSelect) {
        Company currentCompany =  companyQueryService.getCompanyByName(companyNameNew);
        Company loggedInUserCompanyName =  companyQueryService.getCompanyByName(loggedInUserCompany);
        Event event = Event.builder()
                .company(currentCompany)
                .proposalDate1(java.sql.Date.valueOf(eventView.getDate1()))
                .proposalDate2(java.sql.Date.valueOf(eventView.getDate2()))
                .proposalDate3(java.sql.Date.valueOf(eventView.getDate3()))
                .status(EventStatusTypes.PENDING.name())
                .proposalLocation(eventView.getProposalLocation())
                .eventType(eventTypeQueryService.queryEventIdByEventType(eventTypeSelect))
                .dateCreated(java.sql.Date.valueOf(LocalDate.now()))
                .createdBy(loggedInUserCompanyName)
                .build();

        eventRepository.save(event);
    }
}
