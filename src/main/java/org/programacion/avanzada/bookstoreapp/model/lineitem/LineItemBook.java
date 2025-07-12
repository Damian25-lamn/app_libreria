package org.programacion.avanzada.bookstoreapp.model.lineitem;

import lombok.*;
import org.programacion.avanzada.bookstoreapp.model.Book;

@Data
@AllArgsConstructor
public class LineItemBook {
    private LineItem lineItem;
    private Book book;
}
