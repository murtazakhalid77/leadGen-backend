package com.leadgen.backend.model;

import com.leadgen.backend.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ReportUser  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String note;
    @ManyToOne
    @JoinColumn(name = "report_from_user_id")
    private User reportFromUser;
    @ManyToOne
    @JoinColumn(name = "report_to_user_id")
    private User reportToUser;
}
