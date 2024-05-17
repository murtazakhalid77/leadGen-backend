package com.leadgen.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leadgen.backend.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class UserReviews extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String note;
    Long rating;

    @OneToOne
    @JoinColumn(name = "user_request")
    UserRequest userRequest;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;
}
