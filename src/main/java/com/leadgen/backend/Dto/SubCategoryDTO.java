package com.leadgen.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import org.hibernate.annotations.Immutable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Immutable
public class SubCategoryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty(message = "sub category cannot be empty")
    String subCategoryName;
}
