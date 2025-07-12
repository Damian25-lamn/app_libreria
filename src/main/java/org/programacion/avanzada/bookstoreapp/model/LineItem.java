package org.programacion.avanzada.bookstoreapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("line_items")
public class LineItem {

    @Id
    private Integer id;

    @Column("order_id")
    private Integer orderId;
    private int quantity;
    private String bookIsbn;
}