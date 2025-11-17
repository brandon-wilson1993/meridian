package com.meridian.api.users;

import java.util.List;

public interface UsersService {

    Users createUser(Users author);

    void deleteUserById(Long id);

    List<Users> getAllUsers();

    Users getUserById(Long id);

    Users updateUser(Users updatedAuthor, Long id);
}
