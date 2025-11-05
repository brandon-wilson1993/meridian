package com.meridian.api.author;

public interface AuthorService {

    Author createAuthor(Author author);

    void deleteAuthorById(Long id);

    java.util.List<Author> getAllAuthors();

    Author getAuthorById(Long id);

    Author updateAuthor(Author updatedAuthor, Long id);
}
