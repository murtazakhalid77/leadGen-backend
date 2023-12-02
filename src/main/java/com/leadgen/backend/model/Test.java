package com.leadgen.backend.model;

import com.leadgen.backend.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Test extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String tesname;
}
