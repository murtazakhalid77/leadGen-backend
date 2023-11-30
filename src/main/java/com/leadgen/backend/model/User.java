package com.leadgen.backend.model;

import com.leadgen.backend.audit.Auditable;
import com.leadgen.backend.enums.UserType;
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
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstName;
    String lastName;
    String userName;
    String email;
    String deviceId;
    Long OTP;
    Boolean otpFlag;
    String nationalIdentificationNumber;
    byte[] profilePic;
    boolean status;
    @ManyToMany
    @JoinTable(
            name = "user_location",
            joinColumns = @JoinColumn(name = "user_id"), // Name of the join column referring to User's ID
            inverseJoinColumns = @JoinColumn(name = "location_id") // Name of the join column referring to Location's ID
    )
    private List<Location> locations;;

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
