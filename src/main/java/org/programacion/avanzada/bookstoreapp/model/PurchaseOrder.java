package org.programacion.avanzada.bookstoreapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("purchaseorder")
public class PurchaseOrder {
    @Id
    private Long id;

    private Long customerId;     // FK hacia customer.id
    private String status;
    private LocalDate placedon;
    private LocalDate deliveredon;
    private double total;
}
