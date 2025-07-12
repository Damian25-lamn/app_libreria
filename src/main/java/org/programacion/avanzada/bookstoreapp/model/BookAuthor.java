package org.programacion.avanzada.bookstoreapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("book_author")
public class BookAuthor {

    private String booksIsbn;  // FK a Book
    private Integer authorsId; // FK a Author
}
