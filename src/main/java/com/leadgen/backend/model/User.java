package com.leadgen.backend.model;

import com.leadgen.backend.audit.Auditable;
import com.leadgen.backend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Location> locations = new ArrayList<>();


    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserReviews> reviews; //one user revibviw will have many user.

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany
    @JoinColumn(name= "user_id")
    private List<Category> sellingCategory;

    @OneToMany
    @JoinColumn(name ="user_id")
    private List<UserRequest> userRequests;
}
