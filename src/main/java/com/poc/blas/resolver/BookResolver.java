package com.poc.blas.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.poc.blas.model.Author;
import com.poc.blas.model.Book;
import com.poc.blas.repository.AuthorRepository;

import java.util.Optional;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> getAuthor(Book book) {
        return authorRepository.findById(book.getAuthor().getId());
    }
}
