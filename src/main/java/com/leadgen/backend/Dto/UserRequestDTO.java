package com.leadgen.backend.Dto;

import com.leadgen.backend.model.Bid;
import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDTO {
    Long id;
    @NotEmpty(message = "Request cannot be empty")
    private String request;

    private Boolean approvedBySystem;

    private Boolean approvedByAdmin;

    private Long price;
    @NotNull(message = "Category list cannot be null")
    @Size(min = 1, message = "At least one category must be present")
    private List<CategoryDTO> category;

    // Assuming BidDTO represents the DTO for Bid entities
    private List<BidDTO> bids;


    private User user;

}
