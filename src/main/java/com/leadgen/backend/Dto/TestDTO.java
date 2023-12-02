package com.leadgen.backend.Dto;

import com.leadgen.backend.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class TestDTO {


        Long id;
        String tesname;


}
