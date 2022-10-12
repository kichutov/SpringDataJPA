package com.kichutov.project.repositories;

import com.kichutov.project.models.Book;
import com.kichutov.project.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOwner(Person person);

    Optional<Book> findFirstByTitleStartingWith(String string);
}
