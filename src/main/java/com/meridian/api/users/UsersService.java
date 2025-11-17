package com.meridian.api.users;

public interface AuthorService {

    Users createAuthor(Users author);

    void deleteAuthorById(Long id);

    java.util.List<Users> getAllAuthors();

    Users getAuthorById(Long id);

    Users updateAuthor(Users updatedAuthor, Long id);
}
