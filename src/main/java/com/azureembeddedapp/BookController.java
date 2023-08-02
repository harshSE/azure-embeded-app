package com.azureembeddedapp;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;
import java.util.Random;

@Controller("/book")
public class BookController {

  private final BookRepository bookRepository;
  private Random random = new Random();

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Get("/createAndGet")
  public Book createAndGet() {
    Book test = new Book(String.valueOf(random.nextInt()), "TEST");
    return bookRepository.save(test);
  }

  @Get
  public List<Book> getAll() {
    return bookRepository.findAll();
  }

}
