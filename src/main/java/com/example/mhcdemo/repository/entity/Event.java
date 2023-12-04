package com.example.mhcdemo.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Column(name = "proposal_date_1")
    private Date proposalDate1;

    @Column(name = "proposal_date_2")
    private Date proposalDate2;

    @Column(name = "proposal_date_3")
    private Date proposalDate3;

    @Column(name = "approved_date")
    private Date approvedDate;

    private String status;

    private String remarks;

    @Column(name = "date_created")
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name="created_by", referencedColumnName = "id")
    private Company createdBy;

    @Column(name = "proposal_location")
    private String proposalLocation;

    @ManyToOne
    @JoinColumn(name = "event_type_id", referencedColumnName = "id")
    private EventType eventType;
}
