package com.leadgen.backend.Dto;

import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDto {

    String title;
    String description;
    CategoryDTO categoryy;
    LocationDTO locationModel;
    String createdDate;
    String number;
    String price;

}

