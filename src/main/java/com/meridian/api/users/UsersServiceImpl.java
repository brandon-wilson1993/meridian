package com.meridian.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired private UsersRepository usersRepository;

    public Users createUser(Users author) {

        return usersRepository.save(author);
    }

    public void deleteUserById(Long id) {

        if (!usersRepository.existsById(id)) {

            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        usersRepository.deleteById(id);
    }

    public List<Users> getAllUsers() {

        return usersRepository.findAll();
    }

    public Users getUserById(Long id) {

        return usersRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    public Users updateUser(Users updatedAuthor, Long id) {

        return usersRepository
                .findById(id)
                .map(
                        user -> {
                            user.setFirstName(updatedAuthor.getFirstName());
                            user.setLastName(updatedAuthor.getLastName());
                            return usersRepository.save(user);
                        })
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with id " + id + " not found"));
    }
}
