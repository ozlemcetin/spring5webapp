package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        Publisher publisher = null;
        {
            publisher = new Publisher();
            publisher.setName("foo");
            publisher.setAddress("12th Street, LA");
            publisher.setCity("Los Angeles");
            publisher.setState("California");
            publisher.setZip("9988");
            publisherRepository.save(publisher);

            System.out.println("Number of publishers : " + publisherRepository.count());
        }

        Book mdd = null;
        {
            mdd = new Book("Model Driven Design", "123456");
            mdd.setPublisher(publisher);
            bookRepository.save(mdd);

            publisher.getBooks().add(mdd);
            publisherRepository.save(publisher);

        }

        Book noEJB = null;
        {
            noEJB = new Book("J2EE Development without EJB", "23444");
            noEJB.setPublisher(publisher);
            bookRepository.save(noEJB);

            publisher.getBooks().add(noEJB);
            publisherRepository.save(publisher);
        }


        //Eric
        {
            Author eric = new Author("Eric", "Evans");
            eric.getBooks().add(mdd);
            authorRepository.save(eric);

            mdd.getAuthors().add(eric);
            bookRepository.save(mdd);

        }


        //Rod
        {
            Author rod = new Author("Rod", "Johnson");
            rod.getBooks().add(noEJB);
            authorRepository.save(rod);

            noEJB.getAuthors().add(rod);
            bookRepository.save(noEJB);
        }


        System.out.println("Number of books : " + bookRepository.count());

        System.out.println("Number of authors : " + authorRepository.count());
    }
}
