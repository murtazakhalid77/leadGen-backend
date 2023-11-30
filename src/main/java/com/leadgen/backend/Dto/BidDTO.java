package com.leadgen.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidDTO  {
        private Long id;

        @NotEmpty(message = "Amount cannot be empty")
        private String amount;

        @NotNull(message = "Bid from user ID cannot be null")
        private Long bidFromUserId;

        @NotNull(message = "Bid to user ID cannot be null")
        private Long bidToUserId;
}
