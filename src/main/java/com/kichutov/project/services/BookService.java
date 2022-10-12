package com.kichutov.project.services;

import com.kichutov.project.models.Book;
import com.kichutov.project.models.Person;
import com.kichutov.project.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> index() {
        return bookRepository.findAll();
    }

    public List<Book> index(Integer page, Integer booksPerPage, Boolean isSortByYear) {
        isSortByYear = isSortByYear == null ? false : isSortByYear;
        if (page != null && booksPerPage != null) {
             return isSortByYear ? bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent()
                    : bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        } else {
            return isSortByYear ? bookRepository.findAll(Sort.by("year"))
                    : bookRepository.findAll();
        }
    }

    public Optional<Book> show(int id) {
        return bookRepository.findById(id);
    }

    public Optional<Person> getBookOwner(int id) {
        Book book = bookRepository.findById(id).get();
        return Optional.ofNullable(book.getOwner());
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public void release(int id) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(null);
        book.setAssignAt(null);
        bookRepository.save(book);
    }

    public void assign(int id, Person person) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(person);
        book.setAssignAt(LocalDateTime.now());
        bookRepository.save(book);
    }

    public Optional<Book> search(String string) {
        return bookRepository.findFirstByTitleStartingWith(string);
    }
}
