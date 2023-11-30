package com.leadgen.backend.model;

import com.leadgen.backend.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

public class UserRequest extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String request;
    Boolean approvedBySystem;
    Boolean approvedByAdmin;


    @ManyToMany
    @JoinTable(
            name = "req_category",
            joinColumns = @JoinColumn(name = "req_id"), // Name of the join column referring to User's ID
            inverseJoinColumns = @JoinColumn(name = "category_id"))// Name of the join column referring to Location's ID

    List<Category> category;

            @OneToMany
            @JoinColumn(name="user_request_id")
            private List<Bid> bids;





}
