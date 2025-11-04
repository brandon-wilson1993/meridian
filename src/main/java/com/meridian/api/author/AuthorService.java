package com.meridian.api.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorsRepository;

    public Author createAuthor(Author author) {

        return authorsRepository.save(author);
    }

    public void deleteAuthorById(Long id) {

        if (!authorsRepository.existsById(id)) {

            throw new AuthorNotFoundException(id);
        }

        authorsRepository.deleteById(id);
    }

    public List<Author> getAllAuthors() {

        return authorsRepository.findAll();
    }

    public Author getAuthorById(Long id) {

        return authorsRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public Author updateAuthor(Author updatedAuthor, Long id) {

        if(!authorsRepository.existsById(id)) {

            throw new AuthorNotFoundException(id);
        }

        return authorsRepository
                .findById(id)
                .map(
                        platform -> {
                            platform.setFirstName(updatedAuthor.getFirstName());
                            platform.setLastName(updatedAuthor.getLastName());
                            return authorsRepository.save(platform);
                        })
                .orElseGet(
                        () -> {
                            return authorsRepository.save(updatedAuthor);
                        });
    }
}
