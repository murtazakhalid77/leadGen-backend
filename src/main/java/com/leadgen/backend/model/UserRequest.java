package com.leadgen.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leadgen.backend.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class UserRequest extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String description;
    String title;

    Boolean approvedBySystem;
    Boolean needsAdminApproval;
    String price;
    Long notifiedNumber;
    Boolean notifiable;
    Boolean status;

    private String location;


    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany
            @JoinColumn(name="user_request_id")
            private List<Bid> bids;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

}
