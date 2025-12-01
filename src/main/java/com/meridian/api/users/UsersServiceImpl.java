package com.meridian.api.users;

import com.meridian.api.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UsersDTO createUser(UsersDTO usersDTO) {

        Users users = usersRepository.save(modelMapper.map(usersDTO, Users.class));

        return modelMapper.map(users, UsersDTO.class);
    }

    public void deleteUserById(Long id) {

        if (!usersRepository.existsById(id)) {

            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        usersRepository.deleteById(id);
    }

    public List<UsersDTO> getAllUsers() {

        List<Users> list = usersRepository.findAll();

        return list.stream()
                .map(users -> modelMapper.map(users, UsersDTO.class))
                .collect(Collectors.toList());
    }

    public UsersDTO getUserById(Long id) {

        if (!usersRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }

        Optional<Users> user = usersRepository.findById(id);

        UsersDTO usersDTO = modelMapper.map(user.get(), UsersDTO.class);

        return usersDTO;
    }

    public UsersDTO updateUser(UsersDTO usersDTO, Long id) {

        Optional<Users> users = usersRepository.findById(id);

        return users
                .map(
                        user -> {
                            user.setFirstName(usersDTO.getFirstName());
                            user.setLastName(usersDTO.getLastName());
                            Users savedUser = usersRepository.save(user);
                            System.out.println("Updated User: " + savedUser);
                            return modelMapper.map(savedUser, UsersDTO.class);
                        })
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with id " + id + " not found"));
    }
}
