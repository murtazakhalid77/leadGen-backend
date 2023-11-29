package com.leadgen.backend.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ReportUser {
    Long id;
    String note;
    @ManyToOne
    @JoinColumn(name = "report_from_user_id")
    private User reportFromUser;
    @ManyToOne
    @JoinColumn(name = "report_to_user_id")
    private User reportToUser;
}
