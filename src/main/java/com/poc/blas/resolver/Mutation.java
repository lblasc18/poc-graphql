package com.poc.blas.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.poc.blas.exception.BookNotFoundException;
import com.poc.blas.model.Author;
import com.poc.blas.model.Book;
import com.poc.blas.repository.AuthorRepository;
import com.poc.blas.repository.BookRepository;

import java.util.Optional;

public class Mutation implements GraphQLMutationResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorRepository.save(author);

        return author;
    }

    public Book newBook(String title, String isbn, Integer pageCount, Long authorId) {
        Book book = new Book();
        book.setAuthor(new Author(authorId));
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount != null ? pageCount : 0);

        bookRepository.save(book);

        return book;
    }

    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long id) {
        Optional<Book> book = bookRepository.findById(id);
        Book getBook = book.get();

        if(getBook == null) {
            throw new BookNotFoundException("The book to be updated was not found", id);
        }

        getBook.setPageCount(pageCount);

        bookRepository.save(getBook);

        return getBook;
    }
}
