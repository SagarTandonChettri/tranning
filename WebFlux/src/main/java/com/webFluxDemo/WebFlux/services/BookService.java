package com.webFluxDemo.WebFlux.services;

import com.webFluxDemo.WebFlux.entity.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Mono<Book> create(Book book);

    Mono<Book> findById(Long bookId);

    Flux<Book> findAll();

    Mono<Book> update(Long bookId, Book newBook);

    Mono<Void> delete(Long bookId);

    Mono<Boolean> existById(Long bookId);
}
