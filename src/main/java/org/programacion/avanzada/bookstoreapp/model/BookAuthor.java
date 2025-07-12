package org.programacion.avanzada.bookstoreapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("books_authors")
public class BookAuthor {

    @Id
    @Column("books_isbn")
    private String booksIsbn;

    @Id
    @Column("authors_id")
    private Integer authorsId;
}
