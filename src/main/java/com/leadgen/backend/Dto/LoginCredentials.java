package com.leadgen.backend.Dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder

public class LoginCredentials {

    private String email;
    private String password;


}