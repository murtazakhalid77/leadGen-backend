package com.leadgen.backend.model;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

public class User {
    Long id;
    Long firstName;
    Long lastName;
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
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Location> locations;;  //one user can be in multiple locations

    @OneToMany
    @JoinColumn(name = "id")
    private List<UserReviews> reviews;

    @OneToMany
    @JoinColumn(name="id")
    private List<UserType> userTypes;
}
