package com.meridian.api.users;

import java.util.List;

public interface UsersService {

    UsersDTO createUser(UsersDTO author);

    void deleteUserById(Long id);

    List<UsersDTO> getAllUsers();

    UsersDTO getUserById(Long id);

    UsersDTO updateUser(UsersDTO updatedAuthor, Long id);
}
