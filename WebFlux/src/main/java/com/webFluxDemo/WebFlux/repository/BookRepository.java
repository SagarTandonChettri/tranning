package com.webFluxDemo.WebFlux.repository;

import com.webFluxDemo.WebFlux.entity.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book,Long> {
}
