package com.leadgen.backend.Dto;

import com.leadgen.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportUserDTO {

    private Long id;

    @NotEmpty(message = "Note cannot be empty")
    private String note;

    @NotNull(message = "Report from user cannot be null")
    private Long reportFromUserId;

    @NotNull(message = "Report to user cannot be null")
    private Long reportToUserId;
}
