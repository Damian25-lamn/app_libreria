package org.programacion.avanzada.bookstoreapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_orders")
public class PurchaseOrder {
    @Id
    private Integer id;

    @Column("customer_id")
    private Integer customerId;
    private double total;
    private int status;
    @Column("placed_on")
    private LocalDateTime placedOn;
    @Column("delivered_on")
    private LocalDateTime deliveredOn;

}
