package com.leadgen.backend.Dto;

import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDto {

    Long id;
    String title;
    String description;
    CategoryDTO categoryy;
    String locationModel;
    String createdDate;
    String email;
    String price;
    UserDTO user;
    String accepted;
    UserDTO acceptedSeller;
    Long acceptedAmount;
    Boolean status;

}

