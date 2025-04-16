package com.webFluxDemo.WebFlux.serviceImpl;

import com.webFluxDemo.WebFlux.entity.Book;
import com.webFluxDemo.WebFlux.repository.BookRepository;
import com.webFluxDemo.WebFlux.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    @Override
    public Mono<Book> create(Book book) {
        log.info("Received Request to Create Book Entry: {}",book);
        return bookRepository.save(book).log();
    }

    @Override
    public Mono<Book> findById(Long bookId) {
        log.info("Received Request to findById ID: {}",bookId);
        return bookRepository.findById(bookId).log();
    }

    @Override
    public Flux<Book> findAll() {
        log.info("Received Request to findAll");
        return bookRepository.findAll().log();
    }

    @Override
    public Mono<Book> update(Long bookId, Book newBook) {
        log.info("Received Request to Update book");
        log.info("Received newBook Detail: {}", newBook);

        return bookRepository.findById(bookId)
                .doOnNext(oldBook -> log.info("Found oldBook Detail: {}", oldBook))
                .flatMap(existingBook -> {
                    existingBook.setName(newBook.getName());
                    existingBook.setAbout(newBook.getAbout());
                    return bookRepository.save(existingBook)
                            .doOnNext(updated -> log.info("Updated book saved: {}", updated));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Book not found with id: " + bookId)))
                .onErrorResume(ex -> {
                    log.error("Error while updating book: {}", ex.getMessage(), ex);
                    return Mono.error(ex);
                });
    }


    @Override
    public Mono<Void> delete(Long bookId) {
        log.info("Received Request to delete Book detail remove Id: {}",bookId);
        return bookRepository
                .findById(bookId)
                .log()
                .flatMap( book -> bookRepository.delete((book)))
                .log();
    }

    @Override
    public Mono<Boolean> existById(Long bookId) {
        return bookRepository.existsById(bookId);
    }
}
