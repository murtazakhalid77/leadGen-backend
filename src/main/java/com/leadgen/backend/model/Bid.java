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
public class Bid extends Auditable {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    String amount;

    @ManyToOne
    @JoinColumn(name = "bid_from_user_id")
    private User bidFromUserId;
    @ManyToOne
    @JoinColumn(name = "bid_to_user_id")
    private User bidToUserId;


}
