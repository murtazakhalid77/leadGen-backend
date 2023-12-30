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
    @ManyToMany
    @JoinTable(
            name = "req_category",
            joinColumns = @JoinColumn(name = "req_id"), // Name of the join column referring to User's ID
            inverseJoinColumns = @JoinColumn(name = "category_id"))// Name of the join column referring to Location's ID

    List<Category> category;

            @OneToMany
            @JoinColumn(name="user_request_id")
            private List<Bid> bids;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;




}
