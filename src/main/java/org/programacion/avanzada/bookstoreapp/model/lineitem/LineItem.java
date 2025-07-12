package org.programacion.avanzada.bookstoreapp.model.lineitem;

import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("lineitem")
public class LineItem {
    private Long orderId;
    private String bookIsbn;
    private int quantity;
    private int idx;
}