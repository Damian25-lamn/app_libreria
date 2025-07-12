package org.programacion.avanzada.bookstoreapp.model.lineitem;

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
    private Long id;

    @Column("order_id")
    private Long orderId;
    private int quantity;
    private String bookIsbn;


}