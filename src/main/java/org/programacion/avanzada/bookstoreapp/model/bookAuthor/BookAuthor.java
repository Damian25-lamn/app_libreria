package org.programacion.avanzada.bookstoreapp.model.bookAuthor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("books_authors")
public class BookAuthor {

    @Id
    private BookAuthorId id;

    @Transient
    public String getBooksIsbn() {
        return id != null ? id.getBooksIsbn() : null;
    }

    @Transient
    public Integer getAuthorsId() {
        return id != null ? id.getAuthorsId() : null;
    }
}
