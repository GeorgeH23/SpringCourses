package com.george.abuildingaspringbootwebapp.bootstrap;


import com.george.abuildingaspringbootwebapp.domain.Address;
import com.george.abuildingaspringbootwebapp.domain.Author;
import com.george.abuildingaspringbootwebapp.domain.Book;
import com.george.abuildingaspringbootwebapp.domain.Publisher;
import com.george.abuildingaspringbootwebapp.repositories.AuthorRepository;
import com.george.abuildingaspringbootwebapp.repositories.BookRepository;
import com.george.abuildingaspringbootwebapp.repositories.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component // Will tell Spring to detect this as a Spring Manage Component
public class BootStrapData implements CommandLineRunner {

    //dependency injection
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) {

        Address address = new Address("21 Book Shop", "Library Street", "Bucharest", "Ilfov", "012345");
        Publisher nemira = new Publisher("Nemira", address);

        Address address1 = new Address("22 Book Shop", "Library Street", "Bucharest", "Ilfov", "012367");
        Publisher coresi = new Publisher("Coresi", address1);

        publisherRepository.save(nemira);
        publisherRepository.save(coresi);

        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "123123");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        ddd.setPublisher(nemira);
        nemira.getBooks().add(ddd);

        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisherRepository.save(nemira);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "321321");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);
        noEJB.setPublisher(coresi);
        coresi.getBooks().add(noEJB);

        authorRepository.save(rod);
        bookRepository.save(noEJB);
        publisherRepository.save(coresi);

        log.debug("Started in Bootstrap");
        log.debug("Number of publishers: " + publisherRepository.count());
        log.debug("Number of Books: " + bookRepository.count());

    }
}
