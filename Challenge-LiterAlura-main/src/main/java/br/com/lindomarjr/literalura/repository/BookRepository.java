package br.com.lindomarjr.literalura.repository;

import br.com.lindomarjr.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Book findBookByBookTitle(String bookTitle);
}
