package org.example.lab6.repository;

import org.example.lab6.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE " +
            "(:keyword IS NULL OR b.title LIKE %:keyword% OR b.author LIKE %:keyword%) AND " +
            "(:categoryId IS NULL OR b.category.id = :categoryId)")
    List<Book> searchBooks(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);
}
