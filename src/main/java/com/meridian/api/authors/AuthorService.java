package com.meridian.api.authors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorsRepository;

    public Author createAuthor(Author author) {

        return authorsRepository.save(author);
    }

    public Author getAuthorById(Long id) {

        return authorsRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }
}
