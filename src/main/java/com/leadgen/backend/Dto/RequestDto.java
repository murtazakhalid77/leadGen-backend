package com.leadgen.backend.Dto;

import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDto {

    String title;
    String description;
    CategoryDTO category;
    LocationDTO location;
    String number;
    String price;
}

