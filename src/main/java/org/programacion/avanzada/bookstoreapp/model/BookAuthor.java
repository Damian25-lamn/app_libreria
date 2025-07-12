package org.programacion.avanzada.bookstoreapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("books_authors")
public class BookAuthor {

    @Column("books_isbn")
    private String booksIsbn;
    @Column("authors_id")
    private Integer authorsId;
}
