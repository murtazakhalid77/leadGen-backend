package com.leadgen.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.leadgen.backend.audit.Auditable;
import com.leadgen.backend.enums.UserType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstName;
    String lastName;
    String userName;
    String password;
    String phoneNumber;
    String email;
    String deviceId;
    String OTP;
    Boolean otpFlag;
    String nationalIdentificationNumber;
    byte[] profilePic;
    boolean status;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserReviews> reviews; //one user revibviw will have many user.

    @Enumerated(EnumType.STRING)
    private UserType userType;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_category",
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> sellingCategory = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<UserRequest> userRequests;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    String uid;
}
