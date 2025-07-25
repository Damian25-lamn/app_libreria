package org.programacion.avanzada.bookstoreapp.model.bookAuthor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorId implements Serializable {
    private String booksIsbn;
    private Integer authorsId;
}