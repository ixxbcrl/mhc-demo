package com.example.mhcdemo.application.controllers;

import com.example.mhcdemo.application.command.EventCommandService;
import com.example.mhcdemo.dto.EventView;
import com.example.mhcdemo.enums.CompanyTypes;
import com.example.mhcdemo.enums.EventStatusTypes;
import com.example.mhcdemo.application.query.AccountService;
import com.example.mhcdemo.application.query.CompanyQueryService;
import com.example.mhcdemo.application.query.EventTypeQueryService;
import com.example.mhcdemo.repository.EventRepository;
import com.example.mhcdemo.repository.EventTypeRepository;
import com.example.mhcdemo.repository.entity.Account;
import com.example.mhcdemo.repository.entity.Event;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DemoController {

    EventTypeRepository eventTypeRepository;
    EventRepository eventRepository;
    EventCommandService eventCommandService;
    AccountService accountService;
    CompanyQueryService companyQueryService;
    EventTypeQueryService eventTypeQueryService;

    public DemoController(EventTypeRepository eventTypeRepository, EventRepository eventRepository,
                          EventCommandService eventCommandService, AccountService accountService,
                          CompanyQueryService companyQueryService, EventTypeQueryService eventTypeQueryService) {
        this.eventTypeRepository = eventTypeRepository;
        this.eventRepository = eventRepository;
        this.eventCommandService = eventCommandService;
        this.accountService = accountService;
        this.companyQueryService = companyQueryService;
        this.eventTypeQueryService = eventTypeQueryService;
    }

    @GetMapping()
    public String render(Model model) {
        model.addAttribute("now", new Date());

        return "redirect:/eventDashboard";
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login() {
        return "login";
    }

    @GetMapping(path = "/eventDashboard")
    public String initDashboard(Model model) {
        EventView eventView = new EventView();
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Account accountUser = accountService.loadUserByUsernameApp(loggedInUser.getName());
        eventView.setCompanyName(accountUser.getCompany().getCompanyName());
        eventView.setVendorCompanies(companyQueryService.getCompanyByType(CompanyTypes.VENDOR.name()));

        List<String> eventTypeList = eventTypeQueryService.queryAllEventTypes();
        eventView.setEventTypeList(eventTypeList);

        model.addAttribute("eventView", eventView);
        return "eventDashboard";
    }

    @GetMapping(path = "/get-event")
    public String getEvent(Model model) {
        List<Event> eventList = eventRepository.findAll();
        List<EventView> eventViewList = new ArrayList<>();

        for (Event event : eventList) {
            eventViewList.add(EventView.builder()
                    .companyName(event.getCompany().getCompanyName())
                    .date1(event.getProposalDate1().toLocalDate())
                    .date2(event.getProposalDate2().toLocalDate())
                    .date3(event.getProposalDate3().toLocalDate())
                    .proposalLocation(event.getProposalLocation())
                    .eventType(event.getEventType().type)
                    .build());
        }
        model.addAttribute("newEventList", eventViewList);
        return "eventDashboard :: newEvents";
    }

    @PostMapping(path = "/sign-in")
    public String signIn(@RequestParam(value = "error", defaultValue = "") String error, ModelMap modelMap) {
        if (!error.isEmpty()) {
            modelMap.addAttribute("ERROR", error);
        }

        return "redirect:/eventDashboard";
    }

    @GetMapping(value = "/add-event")
    public String showEvent(Model model) {
        EventView eventView = new EventView();

        model.addAttribute("eventView", eventView);
        return "redirect:/eventDashboard";
    }

    @PostMapping(path = "/add-event")
    public String addEvent(@ModelAttribute(value="eventView") EventView eventView,
            String eventTypeSelect, String vendorCompanySelect, String loggedInUserCompany) {
        eventCommandService.createNewEvent(eventView, vendorCompanySelect, loggedInUserCompany, eventTypeSelect);
        return "redirect:/eventDashboard";
    }

    @PostMapping(path = "/update-event")
    public String updateEvent(@RequestParam("action") String action, @RequestParam("new-event") String newEvent,
                              @ModelAttribute EventView eventView, java.sql.Date dateSelect, String eventRemarks) {
        if (action.toUpperCase().equals(EventStatusTypes.APPROVED.name())) {
            eventCommandService.approveOrRejectEvent(EventStatusTypes.APPROVED.name(), dateSelect,
                    eventRemarks, Long.parseLong(newEvent));
        } else {
            eventCommandService.approveOrRejectEvent(EventStatusTypes.REJECTED.name(), dateSelect,
                    eventRemarks, Long.parseLong(newEvent));
        }

        return "redirect:/eventDashboard";
    }

    @ModelAttribute("newEventList")
    public List<EventView> allEvents() {
        //Great way to bind objects to view. But not a fan of injecting the repository here (sorry for the ugly code).
        List<Event> eventList = eventRepository.findAll();
        List<EventView> eventViewList = new ArrayList<>();

        for (Event event : eventList) {
            List<LocalDate> localDates = new ArrayList<>();
            localDates.add(event.getProposalDate1().toLocalDate());
            localDates.add(event.getProposalDate2().toLocalDate());
            localDates.add(event.getProposalDate3().toLocalDate());

            eventViewList.add(EventView.builder()
                    .eventId(event.getId())
                    .companyName(event.getCompany().getCompanyName())
                    .date1(event.getProposalDate1().toLocalDate())
                    .date2(event.getProposalDate2().toLocalDate())
                    .date3(event.getProposalDate3().toLocalDate())
                    .confirmedDate(event.getApprovedDate()==null?null:event.getApprovedDate().toLocalDate())
                    .proposalLocation(event.getProposalLocation())
                    .eventType(event.getEventType().type)
                    .dates(localDates)
                    .eventStatus(event.getStatus())
                    .dateCreated(event.getDateCreated().toLocalDate())
                    .createdBy(event.getCreatedBy().getCompanyName())
                    .build());
        }

        return eventViewList;
    }
}
