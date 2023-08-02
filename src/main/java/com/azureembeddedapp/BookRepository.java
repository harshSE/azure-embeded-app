package com.azureembeddedapp;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;



@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

  List<Book> findAll();
}