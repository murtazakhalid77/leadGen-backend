package com.leadgen.backend.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class OtpAndPassword {
    private String otp;
    private String password;
}
