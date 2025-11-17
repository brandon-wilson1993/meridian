package com.meridian.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorsRepository;

    public Users createAuthor(Users author) {

        return authorsRepository.save(author);
    }

    public void deleteAuthorById(Long id) {

        if (!authorsRepository.existsById(id)) {

            throw new AuthorNotFoundException("Author with id " + id + " not found");
        }

        authorsRepository.deleteById(id);
    }

    public List<Users> getAllAuthors() {

        return authorsRepository.findAll();
    }

    public Users getAuthorById(Long id) {

        return authorsRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " not found"));
    }

    public Users updateAuthor(Users updatedAuthor, Long id) {

        return authorsRepository
                .findById(id)
                .map(
                        platform -> {
                            platform.setFirstName(updatedAuthor.getFirstName());
                            platform.setLastName(updatedAuthor.getLastName());
                            return authorsRepository.save(platform);
                        })
                .orElseThrow(
                        () -> new AuthorNotFoundException("Author with id " + id + " not found"));
    }
}
