package com.kichutov.project.services;

import com.kichutov.project.models.Book;
import com.kichutov.project.models.Person;
import com.kichutov.project.repositories.BookRepository;
import com.kichutov.project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> index() {
        return personRepository.findAll();
    }

    public Optional<Person> show(int id) {
        return personRepository.findById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        List<Book> ownersBooks = bookRepository.findAllByOwner(optionalPerson.get());
        for (Book book : ownersBooks) {
            if (book.getAssignAt() != null && book.getAssignAt().isBefore(LocalDateTime.now().minusDays(10))) {
                book.setOverdue(true);
            }
        }
        return ownersBooks;
    }

    public void save(Person person) {
        personRepository.save(person);
    }

    public void update(int id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }

    public void delete(int id) {
        personRepository.deleteById(id);
    }
    public Optional<Person> getPersonByFullName(String fullName) {
        return personRepository.findPersonByFullName(fullName);
    }

}
