package org.programacion.avanzada.bookstoreapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("customer")
public class Customer {
    @Id
    private Long id;

    private String name;
    private String email;
    private int version;
}
