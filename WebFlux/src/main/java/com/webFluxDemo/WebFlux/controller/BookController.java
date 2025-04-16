package com.webFluxDemo.WebFlux.controller;

import com.webFluxDemo.WebFlux.entity.Book;
import com.webFluxDemo.WebFlux.dto.BookApiResponse;
import com.webFluxDemo.WebFlux.serviceImpl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @PostMapping
    public Mono<BookApiResponse<Book>> create(@RequestBody Book book){
        log.info("Received a request to create new Book Entry NewBOOK: {}",book);
        return bookService.create(book).log()
                .map(saved -> new BookApiResponse<>(
                "success",
                201,
                "Book created successfully",
                saved
        )).log().onErrorResume(ex -> {
            return  Mono.just(new BookApiResponse<>(
                    "error",
                    500,
                    "Failed to create book: " + ex.getMessage(),
                    null
            ));
        }).log();
    }

    @GetMapping
    public Flux<BookApiResponse<Book>> getAll(){
        log.info("Received a request to Get All");
        return bookService.findAll().log().map(book -> new BookApiResponse<>(
                "success",
                201,
                "Book Get successfully",
                book
        )).log().onErrorResume(ex -> {
            return  Mono.just(new BookApiResponse<>(
                    "error",
                    500,
                    "Failed to Get All: " + ex.getMessage(),
                    null
            ));
        }).log();
    }

    @GetMapping("/get/{id}")
    public Mono<BookApiResponse<Book>> getById(@PathVariable("id") Long bookId){
        log.info("Received a request to GetByID ID: {}",bookId);
        return bookService.findById(bookId).log().map(book -> new BookApiResponse<>(
                "success",
                201,
                "Book Get successfully",
                book
        )).log().switchIfEmpty(Mono.just(new BookApiResponse<>(
                "error",
                404,
                "Book not found with id: " + bookId,
                null
        )
        )).log().onErrorResume(ex -> {
            return  Mono.just(new BookApiResponse<>(
                    "error",
                    500,
                    "Failed to Get All: " + ex.getMessage(),
                    null
            ));
        }).log();
    }

    @PutMapping("/update/{id}")
    public Mono<BookApiResponse<Book>> updateEntryById(@RequestBody Book newBook,@PathVariable("id") Long bookId){
        log.info("Received a request to UpdateBook ByID: {}",bookId);
        return bookService.update(bookId,newBook).log().map(book -> new BookApiResponse<>(
                "success",
                201,
                "Book Update successfully",
                book
        )).log().switchIfEmpty(Mono.just(new BookApiResponse<>(
                                "error",
                                404,
                                "Book not found with id: " + bookId,
                                null)
                ))
                .onErrorResume(ex -> {
            return  Mono.just(new BookApiResponse<>(
                    "error",
                    500,
                    "Failed to Update: " + ex.getMessage(),
                    null
            )
            );
        }).log();
    }

    @DeleteMapping("/delete/{id}")
    public Mono<BookApiResponse<Void>> delEntryById(@PathVariable("id") Long bookId){
        log.info("Received a request to DelBook ByID: {}",bookId);
        return bookService.delete(bookId).map(book -> new BookApiResponse<>(
                "success",
                201,
                "Book Update successfully",
                book
        )).onErrorResume(ex ->{
            return Mono.just(new BookApiResponse<>(
                    "error",
                    500,
                    "Failed to Update: " + ex.getMessage(),
                    null
            ));
        });
    }

}
