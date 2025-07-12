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
@Table("inventory")
public class Inventory {

    @Id
    @Column("book_isbn")
    private String bookIsbn;

    private int sold;
    private int supplied;

}
